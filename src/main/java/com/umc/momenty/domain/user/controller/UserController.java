package com.umc.momenty.domain.user.controller;

import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.domain.user.exception.code.UserSuccessCode;
import com.umc.momenty.domain.user.service.query.UserQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    @Override
    public ApiResponse<UserResDTO.UserProfileDTO> getProfile(
            @PathVariable Long userId
    ){
        return ApiResponse.onSuccess(UserSuccessCode.USER_FOUND, userQueryService.getUserProfile(userId));
    }
}
