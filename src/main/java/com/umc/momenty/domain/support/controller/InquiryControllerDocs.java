package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

public interface InquiryControllerDocs {

    @Operation(summary = "문의내역 상세 조회 API", description = "문의내역 ID를 기반으로 문의내역 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 문의내역")
    })
    ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(Long inquiryId);

    @Operation(summary = "문의하기 API", description = "해당 사용자의 문의를 추가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<Void> createInquiry(Long userId,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "문의 추가 요청 (빈 배열이면 사진 미설정, imageUrl는 최대 2장)\n\n"
                                                    + "**요청 예시:**\n"
                                                    + "- type: \"문의 유형 (예: REPORT, BUG 등)\"\n"
                                                    + "- content: \"이러한 문제가 발생했는데요 ...\"\n"
                                                    + "- images: \"[{\"imageUrl\" : \"https://example.com/inquiry1.jpg\"},{\"imageUrl\" : \"https://example.com/inquiry2.jpg\"}]\"\n",
                                            required = true
                                    )
                                    InquiryReqDTO.InquiryDTO inquiryDTO);

    @Operation(summary = "문의내역 리스트 조회 API",
            description = """
                해당 사용자의 전체 문의내역을 페이지네이션으로 조회합니다.

                - page : 페이지 번호 (0부터 시작)
                - size : 페이지 크기
                - sort : 정렬 기준 (예: createdAt,desc)
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청한 페이지가 범위를 벗어난 경우")
    })
    ApiResponse<InquiryResDTO.InquiryPageDTO> getAllInquiry(
            Long userId,
            @ParameterObject @Parameter(description = "페이지네이션 정보 (page, size, sort)") Pageable pageable);
}
