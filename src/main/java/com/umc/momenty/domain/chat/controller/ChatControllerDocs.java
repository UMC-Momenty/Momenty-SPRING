package com.umc.momenty.domain.chat.controller;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ChatControllerDocs {

    @Operation(summary = "챗봇에게 첫 대화 요청 API", description = "챗봇에게 첫 대화를 요청 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<ChatResDTO.ChatResponse> firstChat(@Parameter(hidden = true) Long userId, @RequestBody ChatReqDTO.ChatRequest request);

    @Operation(summary = "기존 대화에서 챗봇에게 대화 요청 API", description = "기존 대화에서 챗봇에게 대화를 요청 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<ChatResDTO.ChatResponse> chat(@Parameter(hidden = true) Long userId, @PathVariable Long conversationId, @RequestBody ChatReqDTO.ChatRequest request);
}
