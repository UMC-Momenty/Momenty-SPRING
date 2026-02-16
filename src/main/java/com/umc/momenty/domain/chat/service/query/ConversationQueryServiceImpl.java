package com.umc.momenty.domain.chat.service.query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@Service
@RequiredArgsConstructor
public class ConversationQueryServiceImpl implements ConversationQueryService {

	private final ConversationRepository conversationRepository;
	private final UserRepository userRepository;

	@Override
	public List<ChatResDTO.ConversationListDTO> getConversationList(Long userId, Long cursorId, LocalDateTime cursorLastChatDate, int count) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		Pageable pageRequest = PageRequest.of(0, count);

		List<Conversation> conversations;
		if (cursorId == null || cursorLastChatDate == null) {
			conversations = conversationRepository.findFirstPage(user, pageRequest);
		} else {
			conversations = conversationRepository.findByUserAndCursorAndCount(user, cursorId, cursorLastChatDate, pageRequest);
		}

		return conversations.stream()
			.map(ConversationConverter::toConversationListDTO)
			.toList();
	}

	@Override
	public List<ChatResDTO.ConversationListDTO> searchConversationList(Long userId, String keyword, Long cursorId,
		LocalDateTime cursorLastChatDate, int count) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		Pageable pageRequest = PageRequest.of(0, count);

		List<Conversation> conversations;
		if (cursorId == null || cursorLastChatDate == null) {
			conversations = conversationRepository.searchKeywordFirstPage(user, keyword, pageRequest);
		} else {
			conversations = conversationRepository.searchKeywordByUserAndCursorAndCount(user, keyword, cursorId, cursorLastChatDate, pageRequest);
		}

		return conversations.stream()
			.map(ConversationConverter::toConversationListDTO)
			.toList();
	}
}
