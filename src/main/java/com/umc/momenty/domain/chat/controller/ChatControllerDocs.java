package com.umc.momenty.domain.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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

    @Operation(
        summary = "챗봇 첨부파일 업로드용 Presigned URL 발급",
        description = """
                챗봇 첨부파일 업로드를 위한 Presigned URL을 생성합니다.

                - mimeTypes는 업로드할 첨부파일의 타입을 enum으로 전달합니다.
                - 본 API는 이미지를 직접 업로드하지 않고, S3 업로드용 Presigned URL을 반환합니다.
                - 반환된 URL로 PUT 요청 시 반드시 Content-Type을 정해진 MimeType으로 설정해야 합니다.
                - 최대 업로드 가능 첨부파일 개수는 3개입니다.

                - enum 종류
                - AUDIO
                - WAV, MP3, AIFF, AAC, OGG, FLAC
                
                - APPLICATION
                - PDF
                
                - IMAGE
                - PNG, JPEG(jpg 확장자 포함), WEBP, HEIC, HEIF
                
                - TEXT
                - PLAIN
                
                - VIDEO
                - MP4, MPEG, MOV, AVI, X_FLV(x-flv 확장자), MPG, WEBM, WMV, GPP3(3gpp 확장자)
                """
    )
    ApiResponse<List<PresignedUrlResponse>> createAttachment(@Valid @RequestBody ChatReqDTO.AttachmentCreateDTO request);

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

    @Operation(summary = "채팅방 검색 API", description = "특정 키워드가 포함된 채팅방 목록을 검색합니다. 커서 방식으로 가장 최신의 채팅방들을 가져옵니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<ChatResDTO.ConversationListDTO>> searchConversationList(
        @Parameter(description = "사용자 ID")
        @PathVariable Long userId,

        @Parameter(description = "검색할 키워드")
        @RequestParam String keyword,

        @Parameter(description = "가장 마지막으로 받은 채팅방 ID")
        @RequestParam Long cursorId,

        @Parameter(description = "가장 마지막으로 받은 채팅방의 최근 채팅 시간")
        @RequestParam LocalDateTime cursorLastChatDate,

        @Parameter(description = "조회할 채팅방 개수")
        @RequestParam Long count);

    @Operation(summary = "채팅 검색 API", description = "채팅방 내에서 특정 키워드가 포함된 모든 채팅 목록을 검색합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<ChatResDTO.SearchChatDTO>> searchChatList(
        @Parameter(description = "사용자 ID")
        @PathVariable Long userId,

        @Parameter(description = "채팅방 ID")
        @PathVariable Long conversationId,

        @Parameter(description = "검색할 키워드")
        @RequestParam String keyword);
}
