package com.umc.momenty.domain.chat.service.query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umc.momenty.domain.chat.converter.ConversationConverter;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.chat.repository.ConversationRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationQueryServiceImpl implements ConversationQueryService {

	private final ConversationRepository conversationRepository;
	private final UserRepository userRepository;

	@Override
	public List<ChatResDTO.ConversationListDTO> getConversationList(Long userId, Long cursorId, LocalDateTime cursorLastChatDate, Long count) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		List<Conversation> conversations;

		log.info("id: {}, date: {}", cursorId, cursorLastChatDate);
		if (cursorId == null || cursorLastChatDate == null) {
			conversations = conversationRepository.findFirstPage(user, count);
		} else {
			conversations = conversationRepository.findByUserAndCursorAndCount(user, cursorId, cursorLastChatDate, count);
		}

		return conversations.stream()
			.map(ConversationConverter::toConversationListDTO)
			.toList();
	}
}
