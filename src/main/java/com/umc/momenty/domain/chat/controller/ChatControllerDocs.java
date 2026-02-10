package com.umc.momenty.domain.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChatControllerDocs {

    @Operation(summary = "챗봇에게 첫 대화 요청 API", description = "챗봇에게 첫 대화를 요청 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<ChatResDTO.ChatResponse> firstChat(@PathVariable Long userId, @RequestBody ChatReqDTO.ChatRequest request);

    @Operation(summary = "기존 대화에서 챗봇에게 대화 요청 API", description = "기존 대화에서 챗봇에게 대화를 요청 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<ChatResDTO.ChatResponse> chat(@PathVariable Long conversationId, @RequestBody ChatReqDTO.ChatRequest request);

    @Operation(summary = "채팅방 조회 API", description = "지금까지 대화한 채팅방 목록을 조회합니다. 커서 방식으로 가장 최신의 채팅방들을 가져옵니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<ChatResDTO.ConversationListDTO>> getConversationList(
        @Parameter(description = "사용자 ID")
        @PathVariable Long userId,

        @Parameter(description = "가장 마지막으로 받은 채팅방 ID")
        @RequestParam Long cursorId,

        @Parameter(description = "가장 마지막으로 받은 채팅방의 최근 채팅 시간")
        @RequestParam LocalDateTime cursorLastChatDate,

        @Parameter(description = "조회할 채팅방 개수")
        @RequestParam Long count);
}
