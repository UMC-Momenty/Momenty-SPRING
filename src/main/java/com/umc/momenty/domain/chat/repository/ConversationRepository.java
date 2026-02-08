package com.umc.momenty.domain.chat.repository;

import com.umc.momenty.domain.chat.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
