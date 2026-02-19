package com.umc.momenty.domain.chat.service.query;

import com.umc.momenty.domain.chat.converter.ChatConverter;
import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.enums.PetQuestionType;
import com.umc.momenty.domain.chat.enums.Role;
import com.umc.momenty.domain.chat.exception.ChatException;
import com.umc.momenty.domain.chat.exception.code.ChatErrorCode;
import com.umc.momenty.domain.chat.policy.PetDomainPolicy;
import com.umc.momenty.domain.chat.policy.PolicyResult;
import com.umc.momenty.domain.chat.repository.ChatRepository;
import com.umc.momenty.domain.chat.repository.ConversationRepository;
import com.umc.momenty.domain.chat.service.command.AttachmentCommandService;
import com.umc.momenty.domain.chat.service.command.ChatCommandService;
import com.umc.momenty.domain.chat.service.command.ConservationCommandService;
import com.umc.momenty.domain.chat.specialization.classifier.PetQuestionTypeResolver;
import com.umc.momenty.domain.chat.specialization.context.PetChatContextProvider;
import com.umc.momenty.domain.chat.specialization.faq.FaqCategoryResolver;
import com.umc.momenty.domain.chat.specialization.prompt.PetChatPromptBuilder;
import com.umc.momenty.domain.support.entity.FAQ;
import com.umc.momenty.domain.support.enums.FaqCategory;
import com.umc.momenty.domain.support.service.query.FaqQueryService;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.global.infra.gemini.GeminiProvider;
import com.umc.momenty.global.infra.gemini.enums.MimeType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatQueryServiceImpl implements ChatQueryService {

    private final PetDomainPolicy policy;
    private final PetQuestionTypeResolver typeResolver;
    private final PetChatPromptBuilder promptBuilder;
    private final GeminiProvider geminiProvider;
    private final PetChatContextProvider contextProvider;

    private final FaqQueryService faqQueryService;
    private final FaqCategoryResolver faqCategoryResolver;
    private final ChatCommandService chatCommandService;

    private final ConservationCommandService conservationCommandService;
    private final ConversationRepository conservationRepository;

    private final AttachmentCommandService attachmentCommandService;

    private final ChatConverter chatConverter;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public ChatResDTO.ChatResponse firstChat(Long userId, ChatReqDTO.ChatRequest chatRequest) {
        Conversation conversation = conservationCommandService.createConversation(userId, chatRequest.message());

        return processChat(conversation, chatRequest);
    }

    @Override
    public ChatResDTO.ChatResponse chat(Long userId, Long conversationId, ChatReqDTO.ChatRequest chatRequest) {

        Conversation conversation = conservationRepository.findByIdAndUserId(conversationId, userId)
                        .orElseThrow(() -> new ChatException(ChatErrorCode.CONVERSATION_NOT_FOUND));

        return processChat(conversation, userMessage);
    }

    private ChatResDTO.ChatResponse processChat(Conversation conversation, String userMessage) {

        // Chat 엔티티 생성
        Long chatId = chatCommandService.saveUserChat(chatRequest, conversation);

        // Attachment 엔티티 생성
        Map<String, MimeType> attachmentUrls = attachmentCommandService.saveAttachment(chatRequest, chatId);

        // 정책 검사
        PolicyResult policyResult = policy.validate(chatRequest.message());
        if (!policyResult.allowed()) {
            Long botChatId = chatCommandService.saveBotChat(
                    policyResult.rejectMessage(),
                    conversation
            );

            return chatConverter.from(
                    botChatId,
                    Role.BOT,
                    policyResult.rejectMessage(),
                    PetQuestionType.HEALTH_RELATED
            );
        }

        // FAQ 관련
        FaqCategory category = faqCategoryResolver.resolve(chatRequest.message());
        Optional<FAQ> faq = faqQueryService.findByCategory(category);

        String faqContext = faq.map(value -> "FAQ 참고: " + value.getAnswer()).orElse("");

        // 질문 분류
        PetQuestionType type = typeResolver.resolve(chatRequest.message());

        // 컨텍스트 확보
        String context = contextProvider.getRelevantContext(type, chatRequest.message());
        if (!faqContext.isEmpty()) {
            context += "\n" + faqContext;
        }

        // 프롬프트 + Gemini
        String prompt = promptBuilder.build(type, context);

        String aiMessage;
        // 첨부파일 포함 여부 분류
        if (attachmentUrls.isEmpty()) {
            aiMessage = geminiProvider.generateTextContent(prompt, chatRequest.message());
        } else {
            aiMessage = geminiProvider.generateTextContentWithFiles(prompt, chatRequest.message(), attachmentUrls);
        }

        if (aiMessage == null || aiMessage.isBlank()) {
            throw new ChatException(ChatErrorCode.AI_RESPONSE_FAILED);
        }

        String finalAnswer = appendDisclaimerIfNeeded(type, aiMessage);

        // 최근 대화 업데이트
        conversation.updateLastMessage(finalAnswer);

        Long botChatId = chatCommandService.saveBotChat(finalAnswer, conversation);

        return chatConverter.from(botChatId, Role.BOT, finalAnswer, type);
    }

    private String appendDisclaimerIfNeeded(PetQuestionType type, String message) {
        if (type == PetQuestionType.HEALTH_RELATED) {
            return message + "\n\n 정확한 진단과 치료는 수의사 상담이 필요해요.";
        }
        return message;
    }
}
