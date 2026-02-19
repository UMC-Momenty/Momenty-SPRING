package com.umc.momenty.domain.user.controller;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.oauth.dto.TokenDto;
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
    ApiResponse<TokenDto> updateProfile(
            @Parameter(description = "수정할 사용자 ID")
            Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "프로필 수정 내용 (PATCH 방식)\n\n"
                            + "**요청 규칙:**\n"
                            + "- 전달되지 않은 필드는 기존 값을 유지합니다.\n"
                            + "- profileUrl이 빈 문자열(\"\")일 경우 null로 초기화됩니다.\n"
                            + "- resetQuestTime : true 일 경우 questTime은 null로 초기화됩니다.\n\n"
                            + "**응답 규칙:**\n"
                            + "- 권한(ROLE)이 변경되는 경우에만 TokenDto(accessToken/refreshToken)가 반환됩니다.\n"
                            + "- 권한 변경이 없으면 result는 null 입니다.\n\n"
                            + "**요청 예시:**\n"
                            + "- \"username\": \"example\",\n"
                            + "- \"gender\": \"FEMALE\",\n"
                            + "- \"birth\": \"2000-01-01\",\n"
                            + "- \"profileUrl\": \"https://...\",\n"
                            + "- \"questTime\": \"13:40\"\n"
                            + "- \"resetQuestTime\": true\n",
                    required = true
            )
            UserReqDTO.UserProfileDTO userReqDTO
    );
}
