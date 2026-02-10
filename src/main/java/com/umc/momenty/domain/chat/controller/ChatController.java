package com.umc.momenty.domain.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.exception.code.ChatSuccessCode;
import com.umc.momenty.domain.chat.service.query.ChatQueryService;
import com.umc.momenty.domain.chat.service.query.ConversationQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatQueryService chatQueryService;
    private final ConversationQueryService conversationQueryService;

    @PostMapping("/users/{userId}")
    @Override
    public ApiResponse<ChatResDTO.ChatResponse> firstChat(@PathVariable Long userId, @RequestBody ChatReqDTO.ChatRequest request){
        return ApiResponse.onSuccess(ChatSuccessCode.CHAT_CREATED, chatQueryService.firstChat(userId, request.message()));
    }

    @PostMapping("/conversations/{conversationId}")
    @Override
    public ApiResponse<ChatResDTO.ChatResponse> chat(@PathVariable Long conversationId, @RequestBody ChatReqDTO.ChatRequest request){
        return ApiResponse.onSuccess(ChatSuccessCode.CHAT_CREATED, chatQueryService.chat(conversationId, request.message()));
    }

    @GetMapping("/users/{userId}/conversations")
    @Override
    public ApiResponse<List<ChatResDTO.ConversationListDTO>> getConversationList(
        @PathVariable Long userId,
        @RequestParam(required = false) Long cursorId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorLastChatDate,
        @RequestParam Long count) {
        return ApiResponse.onSuccess(ChatSuccessCode.CONVERSATION_FOUND, conversationQueryService.getConversationList(userId, cursorId, cursorLastChatDate, count));
    }

    @GetMapping("/users/{userId}/conversations/search")
    @Override
    public ApiResponse<List<ChatResDTO.ConversationListDTO>> searchConversationList(
        @PathVariable Long userId,
        @RequestParam String keyword,
        @RequestParam(required = false) Long cursorId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorLastChatDate,
        @RequestParam Long count
    ) {
        return ApiResponse.onSuccess(ChatSuccessCode.CONVERSATION_FOUND, conversationQueryService.searchConversationList(userId, keyword, cursorId, cursorLastChatDate, count));
    }
}
