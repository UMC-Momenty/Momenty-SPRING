package com.umc.momenty.domain.chat.converter;

import org.springframework.stereotype.Component;

import com.umc.momenty.domain.chat.entity.Attachment;
import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.global.infra.gemini.enums.MimeType;

@Component
public class AttachmentConverter {

	public static Attachment toAttachment(String url, MimeType mimeType, Chat chat) {
		return Attachment.builder()
			.type(mimeType)
			.fileUrl(url)
			.chat(chat)
			.build();
	}
}
