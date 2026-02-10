package com.umc.momenty.domain.chat.converter;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.enums.PetQuestionType;
import com.umc.momenty.domain.chat.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class ChatConverter {

    public static Chat toUserChat(String content, Conversation conversation) {
        return Chat.builder()
                .role(Role.USER)
                .content(content)
                .conversation(conversation)
                .build();
    }

    public static Chat toBotChat(String content, Conversation conversation) {
        return Chat.builder()
                .role(Role.BOT)
                .content(content)
                .conversation(conversation)
                .build();
    }

    public ChatResDTO.ChatResponse from(Long chatId, Role role, String answer, PetQuestionType type) {
        return ChatResDTO.ChatResponse.builder()
                .chatId(chatId)
                .role(role)
                .answer(answer)
                .questionType(type)
                .build();
    }
}
