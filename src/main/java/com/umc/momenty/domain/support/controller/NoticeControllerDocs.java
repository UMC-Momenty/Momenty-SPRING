package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface NoticeControllerDocs {

    @Operation(summary = "공지사항 상세 조회 API", description = "공지사항 ID를 기반으로 공지사항 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 공지사항")
    })
    ApiResponse<NoticeResDTO.NoticeDTO> getNotice(@Parameter(description = "조회할 공지사항 ID") Long noticeId);
}
