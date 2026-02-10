package com.umc.momenty.domain.chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

	@Query("""
    SELECT c
    FROM Conversation c
    WHERE c.user = :user
    AND
    	(c.updatedAt < :cursorLastChatDate
    	OR (c.updatedAt = :cursorLastChatDate AND c.id < :cursorId))
    ORDER BY c.updatedAt DESC, c.id DESC
    LIMIT :count
""")
	List<Conversation> findByUserAndCursorAndCount(User user, Long cursorId, LocalDateTime cursorLastChatDate, Long count);

	@Query("""
    SELECT c
    FROM Conversation c
    WHERE c.user = :user
    ORDER BY c.updatedAt DESC, c.id DESC
    LIMIT :count
""")
	List<Conversation> findFirstPage(User user, Long count);
}
