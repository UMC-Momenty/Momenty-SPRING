package com.umc.momenty.domain.chat.service.command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.umc.momenty.domain.chat.converter.AttachmentConverter;
import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.entity.Attachment;
import com.umc.momenty.domain.chat.entity.Chat;
import com.umc.momenty.domain.chat.exception.ChatException;
import com.umc.momenty.domain.chat.exception.code.ChatErrorCode;
import com.umc.momenty.domain.chat.repository.AttachmentRepository;
import com.umc.momenty.domain.chat.repository.ChatRepository;
import com.umc.momenty.global.infra.gemini.enums.MimeType;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import com.umc.momenty.global.infra.s3.service.ImageUploadService;
import com.umc.momenty.global.infra.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentCommandServiceImpl implements AttachmentCommandService {
	private final ImageUploadService imageUploadService;
	private final AttachmentRepository attachmentRepository;
	private final S3Service s3Service;
	private final ChatRepository chatRepository;

	@Override
	public List<PresignedUrlResponse> generate(ChatReqDTO.AttachmentCreateDTO request) {

		if (request.mimeTypes().size() > 3) {
			throw new ChatException(ChatErrorCode.INVALID_ATTACHMENT_COUNT);
		}

		return imageUploadService.generatePresignedUrls(
			"chat",
			request.mimeTypes().stream()
				.map(MimeType::getExtension)
				.toList()
		);
	}

	@Override
	public Map<String, MimeType> saveAttachment(ChatReqDTO.ChatRequest question, Long chatId) {
		Chat chat = chatRepository.findById(chatId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));

		List<Attachment> attachments = question.files().stream()
			.map(dto ->
				AttachmentConverter.toAttachment(s3Service.buildImageUrl(dto.fileKey()), dto.mimeType(), chat)
			)
			.toList();

		attachmentRepository.saveAll(attachments);

		return question.files().stream()
			.collect(Collectors.toMap(
				dto -> s3Service.generatePresignedDownloadUrl(dto.fileKey()).url(),
				ChatReqDTO.AttachmentDTO::mimeType
			));
	}
}
