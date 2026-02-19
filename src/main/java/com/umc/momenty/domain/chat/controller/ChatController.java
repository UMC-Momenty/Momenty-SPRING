package com.umc.momenty.domain.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.exception.code.ChatSuccessCode;
import com.umc.momenty.domain.chat.service.command.AttachmentCommandService;
import com.umc.momenty.domain.chat.service.query.ChatQueryService;
import com.umc.momenty.domain.chat.service.query.ConversationQueryService;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatQueryService chatQueryService;
    private final ConversationQueryService conversationQueryService;
    private final AttachmentCommandService attachmentCommandService;

    @PostMapping("/users")
    @Override
    public ApiResponse<ChatResDTO.ChatResponse> firstChat(@AuthUser Long userId, @RequestBody ChatReqDTO.ChatRequest request){
        return ApiResponse.onSuccess(ChatSuccessCode.CHAT_CREATED, chatQueryService.firstChat(userId, request.message()));
    }

    @PostMapping("/conversations/{conversationId}")
    @Override
    public ApiResponse<ChatResDTO.ChatResponse> chat(@AuthUser Long userId, @PathVariable Long conversationId, @RequestBody ChatReqDTO.ChatRequest request){
        return ApiResponse.onSuccess(ChatSuccessCode.CHAT_CREATED, chatQueryService.chat(userId, conversationId, request));
    }

    @PostMapping("/attachment")
    @Override
    public ApiResponse<List<PresignedUrlResponse>> createAttachment(@Valid @RequestBody ChatReqDTO.AttachmentCreateDTO request){
        return ApiResponse.onSuccess(ChatSuccessCode.CHAT_ATTACHMENT_CREATED, attachmentCommandService.generate(request));
    }

    @GetMapping("/conversations")
    @Override
    public ApiResponse<List<ChatResDTO.ConversationListDTO>> getConversationList(
        @AuthUser Long userId,
        @RequestParam(required = false) Long cursorId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorLastChatDate,
        @RequestParam int count) {
        return ApiResponse.onSuccess(ChatSuccessCode.CONVERSATION_FOUND, conversationQueryService.getConversationList(userId, cursorId, cursorLastChatDate, count));
    }

    @GetMapping("/conversations/search")
    @Override
    public ApiResponse<List<ChatResDTO.ConversationListDTO>> searchConversationList(
        @AuthUser Long userId,
        @RequestParam String keyword,
        @RequestParam(required = false) Long cursorId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorLastChatDate,
        @RequestParam int count
    ) {
        return ApiResponse.onSuccess(ChatSuccessCode.CONVERSATION_FOUND, conversationQueryService.searchConversationList(userId, keyword, cursorId, cursorLastChatDate, count));
    }

    @GetMapping("/conversations/{conversationId}/search")
    @Override
    public ApiResponse<List<ChatResDTO.SearchChatDTO>> searchChatList(
        @AuthUser Long userId,
        @PathVariable Long conversationId,
        @RequestParam String keyword
    ) {
        return ApiResponse.onSuccess(ChatSuccessCode.CONVERSATION_FOUND, chatQueryService.searchChatList(userId, conversationId, keyword));
    }
}
