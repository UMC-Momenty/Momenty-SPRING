package com.umc.momenty.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umc.momenty.domain.chat.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
