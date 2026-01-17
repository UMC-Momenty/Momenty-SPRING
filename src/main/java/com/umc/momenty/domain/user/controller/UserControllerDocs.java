package com.umc.momenty.domain.user.controller;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


public interface UserControllerDocs {

    @Operation(summary = "사용자 프로필 조회 API", description = "해당 사용자의 프로필을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
    })
    ApiResponse<UserResDTO.UserProfileDTO> getProfile(@Parameter(description = "조회할 사용자 ID") Long userId);

    @Operation(
            summary = "사용자 프로필 수정 API",
            description = "해당 사용자의 프로필을 수정합니다. 일부 필드만 전달해도 기존 값은 유지됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<Void> updateProfile(
            @Parameter(description = "수정할 사용자 ID")
            Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "프로필 수정 내용 (null이면 알림 미설정)\n\n"
                            + "**요청 예시:**\n"
                            + "- username: \"example\"\n"
                            + "- gender: \"FEMALE\"\n"
                            + "- birth: \"2000-01-01\"\n"
                            + "- profileUrl: \"https://...\""
                            + "- questTime: \"13:40\"",
                    required = true
            )
            UserReqDTO.UserProfileDTO userReqDTO
    );
}
