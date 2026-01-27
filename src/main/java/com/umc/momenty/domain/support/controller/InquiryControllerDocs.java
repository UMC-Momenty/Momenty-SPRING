package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface InquiryControllerDocs {

    @Operation(summary = "문의내역 상세 조회 API", description = "문의내역 ID를 기반으로 문의내역 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 문의내역")
    })
    ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(Long inquiryId);
}
