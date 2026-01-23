package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

public interface NoticeControllerDocs {

    @Operation(summary = "공지사항 상세 조회 API", description = "공지사항 ID를 기반으로 공지사항 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 공지사항")
    })
    ApiResponse<NoticeResDTO.NoticeDTO> getNotice(@Parameter(description = "조회할 공지사항 ID") Long noticeId);

    @Operation(summary = "공지사항 리스트 조회 API",
            description = """
                전체 공지사항을 페이지네이션으로 조회합니다.

                - page : 페이지 번호 (0부터 시작)
                - size : 페이지 크기
                - sort : 정렬 기준 (예: createdAt,desc)
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청한 페이지가 범위를 벗어난 경우")
    })
    ApiResponse<NoticeResDTO.NoticePageDTO> getAllNotice(@ParameterObject @Parameter(description = "페이지네이션 정보 (page, size, sort)") Pageable pageable);
}