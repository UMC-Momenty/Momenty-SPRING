package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InquiryControllerDocs {

    @Operation(summary = "문의내역 상세 조회 API", description = "문의내역 ID를 기반으로 문의내역 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 문의내역")
    })
    ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(@Parameter(hidden = true) Long userId, Long inquiryId);

    @Operation(summary = "문의하기 API", description = "해당 사용자의 문의를 추가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<Void> createInquiry(@Parameter(hidden = true) Long userId,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "문의 추가 요청 (빈 배열이면 사진 미설정, imageKey는 최대 2장, Presigned URL로 요청 시 받은 key 사용)\n\n"
                                                    + "**요청 예시:**\n"
                                                    + "- type: \"문의 유형\" (예: ACCOUNT, BUG, REPORT, SUGGESTION, ETC)\n"
                                                    + "- content: \"이러한 문제가 발생했는데요 ...\"\n"
                                                    + "- images: \"[{\"imageKey\" : \"inquiry/0d1d0f96-bf56-4bb8-9c4a-70727a94a368\"},{\"imageUrl\" : \"inquiry/0d1d0f96-bf56-4bb8-9c4a-70727a94a368\"}]\"\n",
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
            @Parameter(hidden = true) Long userId,
            @ParameterObject @Parameter(description = "페이지네이션 정보 (page, size, sort)") Pageable pageable);

    @Operation(
            summary = "문의하기 이미지 업로드용 Presigned URL 발급",
            description = """
                문의 이미지 업로드를 위한 Presigned URL을 생성합니다.

                - imageTypes는 업로드할 이미지의 타입을 enum(JPEG, PNG)으로 전달합니다.
                - 본 API는 이미지를 직접 업로드하지 않고, S3 업로드용 Presigned URL을 반환합니다.
                - 반환된 URL로 PUT 요청 시 반드시 Content-Type을 이미지 타입(image/png, image/jpeg)으로 설정해야 합니다.
                - 최대 업로드 가능 이미지 개수는 2장입니다.
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<List<PresignedUrlResponse>> createInquiryImage(@RequestBody InquiryReqDTO.InquiryImageCreateDTO request);
}
