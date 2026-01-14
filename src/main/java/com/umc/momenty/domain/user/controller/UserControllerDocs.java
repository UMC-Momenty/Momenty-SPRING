package com.umc.momenty.domain.user.controller;

import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


public interface UserControllerDocs {

    @Operation(summary = "사용자 프로필 조회 API", description = "해당 사용자의 프로필을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<UserResDTO.UserProfileDTO> getProfile(Long userId);
}
