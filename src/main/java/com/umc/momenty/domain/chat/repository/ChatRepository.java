package com.umc.momenty.domain.chat.repository;

import java.util.List;

import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.domain.chat.entity.Conversation;
import com.umc.momenty.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	@Query("""
    SELECT ch
    FROM Chat ch
    WHERE ch.conversation.user = :user
    AND ch.conversation = :conversation
    AND ch.content LIKE CONCAT('%', :keyword, '%')
    ORDER BY ch.createdAt DESC
""")
	List<Chat> searchByUserAndKeyword(User user, Conversation conversation, String keyword);
}
