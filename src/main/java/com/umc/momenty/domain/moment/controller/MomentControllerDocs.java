package com.umc.momenty.domain.moment.controller;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.dto.res.MomentResDTO;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MomentControllerDocs {

    @Operation(summary = "모먼트 생성 API", description = "특정 사용자의 특정 반려동물 모먼트를 생성합니다. ")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<Void> createMoment(
            @Parameter(description = "사용자 ID", required = true) Long userId,
            @Parameter(description = "반려동물 ID", required = true) Long petId,
            @RequestBody(
                    description = """
                        **요청 예시**
                        - images: [{"imageKey":"moments/uuid-1"},{"imageKey":"moments/uuid-2"}]
                        - petId: 1,
                        - emotion: "HAPPINESS"
                        - content: "오늘 산책이 정말 즐거웠다!"
                        """,
                    required = true
            )
            @Valid MomentReqDTO.MomentDTO dto
    );

    @Operation(
            summary = "모먼트 이미지 업로드용 Presigned URL 발급",
            description = """
                모먼트 이미지 업로드를 위한 Presigned URL 목록을 생성합니다.
                
                - imageTypes는 업로드할 이미지의 타입을 enum(JPEG, PNG)으로 전달합니다.
                - 본 API는 이미지를 직접 업로드하지 않고, S3 업로드용 Presigned URL을 반환합니다.
                - 반환된 URL로 PUT 요청 시 반드시 Content-Type을 이미지 타입(image/png, image/jpeg)으로 설정해야 합니다.
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<PresignedUrlResponse>> createMomentImage(
            @RequestBody(
                    description = """
                        Presigned URL 생성 요청 바디

                        **요청 예시**
                        - imageKey: "moments/uuid-1"
                        """,
                    required = true
            )
            @Valid MomentReqDTO.MomentImageCreateDTO request
    );

    @Operation(
            summary = "모먼트 리스트 조회 API",
            description = """
                특정 사용자의 특정 반려동물에 속한 모먼트 목록을 최신순으로 페이징 조회합니다.

                - page : 페이지 번호 (0부터)
                - size : 페이지 크기
                - sort : 정렬 기준 (기본: createdAt,DESC)
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<MomentResDTO.MomentPageDTO> getMoments(
            @Parameter(description = "사용자 ID", required = true) Long userId,
            @Parameter(description = "반려동물 ID", required = true) Long petId,
            @ParameterObject @Parameter(description = "페이지네이션 정보 (page, size, sort)") Pageable pageable
    );

    @Operation(
            summary = "모먼트 상세 조회 API",
            description = "특정 사용자(userId)의 특정 반려동물(petId)에 속한 모먼트(momentId) 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<MomentResDTO.MomentDTO> getMoment(
            @Parameter(description = "사용자 ID", required = true) Long userId,
            @Parameter(description = "반려동물 ID", required = true) Long petId,
            @Parameter(description = "모먼트 ID", required = true) Long momentId
    );

    @Operation(
            summary = "모먼트 개수 조회 API",
            description = "특정 사용자(userId)의 특정 반려동물(petId)에 속한 모먼트 개수를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<MomentResDTO.MomentCountDTO> getMomentCount(
            @AuthUser Long userId,
            @Parameter(description = "반려동물 ID", required = true) Long petId
    );
}
