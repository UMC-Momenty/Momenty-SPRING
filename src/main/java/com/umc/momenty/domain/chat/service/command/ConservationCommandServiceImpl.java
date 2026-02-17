package com.umc.momenty.domain.chat.service.command;

import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.repository.ConversationRepository;
import com.umc.momenty.domain.chat.specialization.ConversationTitleGenerator;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConservationCommandServiceImpl implements ConservationCommandService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationTitleGenerator titleGenerator;

    @Override
    public Conversation createConversation(Long userId, String firstMessage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        String title = titleGenerator.generate(firstMessage);

        Conversation conversation = Conversation.builder()
                .user(user)
                .title(title)
                .content(makePreview(firstMessage))
                .build();

        return conversationRepository.save(conversation);
    }

    private String makePreview(String message) {
        int MAX = 60;
        return message.length() > MAX
                ? message.substring(0, MAX) + "..."
                : message;
    }
}
