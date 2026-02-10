package com.umc.momenty.domain.chat.service.query;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;

public interface ConversationQueryService {
	List<ChatResDTO.ConversationListDTO> getConversationList(Long userId, Long cursorId, LocalDateTime cursorLastChatDate, Long count);

	List<ChatResDTO.ConversationListDTO> searchConversationList(Long userId, String keyword, Long cursorId, LocalDateTime cursorLastChatDate, Long count);
}
