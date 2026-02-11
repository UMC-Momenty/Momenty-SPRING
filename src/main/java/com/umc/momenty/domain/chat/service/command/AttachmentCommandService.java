package com.umc.momenty.domain.chat.service.command;

import java.util.List;
import java.util.Map;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.global.infra.gemini.enums.MimeType;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;

public interface AttachmentCommandService {
	List<PresignedUrlResponse> generate(ChatReqDTO.AttachmentCreateDTO request);

	Map<String, MimeType> saveAttachment(ChatReqDTO.ChatRequest chatRequest, Long chatId);
}
