package com.umc.momenty.domain.chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.user.entity.User;

import org.springframework.data.domain.Pageable;
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
""")
	List<Conversation> findByUserAndCursorAndCount(User user, Long cursorId, LocalDateTime cursorLastChatDate, Pageable pageable);

	@Query("""
    SELECT c
    FROM Conversation c
    WHERE c.user = :user
    ORDER BY c.updatedAt DESC, c.id DESC
""")
	List<Conversation> findFirstPage(User user, Pageable pageable);

	@Query("""
    SELECT DISTINCT c
    FROM Chat ch
    JOIN ch.conversation c
    WHERE c.user = :user
    AND ch.content LIKE CONCAT('%', :keyword, '%')
    ORDER BY c.updatedAt DESC, c.id DESC
""")
	List<Conversation> searchKeywordFirstPage(User user, String keyword, Pageable pageable);

	@Query("""
    SELECT DISTINCT c
    FROM Chat ch
    JOIN ch.conversation c
    WHERE
    	c.user = :user
        AND ch.content LIKE CONCAT('%', :keyword, '%')
        AND (
            c.updatedAt < :cursorLastChatDate
            OR (
                c.updatedAt = :cursorLastChatDate
                AND c.id < :cursorId
            )
        )
    ORDER BY c.updatedAt DESC, c.id DESC
""")
	List<Conversation> searchKeywordByUserAndCursorAndCount(User user, String keyword, Long cursorId, LocalDateTime cursorLastChatDate, Pageable pageable);
}
