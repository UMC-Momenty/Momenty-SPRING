package com.umc.momenty.domain.chat.service.query;

import com.umc.momenty.domain.chat.converter.ChatConverter;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.enums.PetQuestionType;
import com.umc.momenty.domain.chat.enums.Role;
import com.umc.momenty.domain.chat.policy.PetDomainPolicy;
import com.umc.momenty.domain.chat.policy.PolicyResult;
import com.umc.momenty.domain.chat.service.command.ChatCommandService;
import com.umc.momenty.domain.chat.service.command.ConservationCommandService;
import com.umc.momenty.domain.chat.specialization.classifier.PetQuestionTypeResolver;
import com.umc.momenty.domain.chat.specialization.context.PetChatContextProvider;
import com.umc.momenty.domain.chat.specialization.faq.FaqCategoryResolver;
import com.umc.momenty.domain.chat.specialization.prompt.PetChatPromptBuilder;
import com.umc.momenty.domain.support.entity.FAQ;
import com.umc.momenty.domain.support.enums.FaqCategory;
import com.umc.momenty.domain.support.service.query.FaqQueryService;
import com.umc.momenty.global.infra.gemini.GeminiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    private final ChatConverter chatConverter;

    @Override
    public ChatResDTO.ChatResponse chat(Long userId, String userMessage) {

        Conversation conversation = conservationCommandService.createConversation(userId, userMessage);

        chatCommandService.saveUserChat(userMessage, conversation);

        // 정책 검사
        PolicyResult policyResult = policy.validate(userMessage);
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
        FaqCategory category = faqCategoryResolver.resolve(userMessage);
        Optional<FAQ> faq = faqQueryService.findByCategory(category);

        String faqContext = faq.map(value -> "FAQ 참고: " + value.getAnswer()).orElse("");

        // 질문 분류
        PetQuestionType type = typeResolver.resolve(userMessage);

        // 컨텍스트 확보
        String context = contextProvider.getRelevantContext(type, userMessage);
        if (!faqContext.isEmpty()) {
            context += "\n" + faqContext;
        }

        // 프롬프트 + Gemini
        String prompt = promptBuilder.build(type, context);
        String aiMessage = geminiProvider.generateTextContent(prompt, userMessage);

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
