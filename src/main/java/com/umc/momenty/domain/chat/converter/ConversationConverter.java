package com.umc.momenty.domain.chat.converter;

import org.springframework.stereotype.Component;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.entity.Conversation;

@Component
public class ConversationConverter {

	public static ChatResDTO.ConversationListDTO toConversationListDTO(Conversation conversation) {
		return ChatResDTO.ConversationListDTO.builder()
			.conversationId(conversation.getId())
			.lastChatDate(conversation.getUpdatedAt())
			.lastMessage(conversation.getContent())
			.build();
	}
}
