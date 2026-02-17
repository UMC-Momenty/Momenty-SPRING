package com.umc.momenty.domain.chat.service.command;

import com.umc.momenty.domain.chat.converter.ChatConverter;
import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatRepository chatRepository;

    @Override
    public Long saveBotChat(String answer, Conversation conversation) {
        Chat chat = ChatConverter.toBotChat(answer, conversation);
        return chatRepository.save(chat).getId();
    }

    @Override
    public void saveUserChat(String question, Conversation conversation) {
        Chat chat = ChatConverter.toUserChat(question, conversation);
        chatRepository.save(chat);
    }
}
