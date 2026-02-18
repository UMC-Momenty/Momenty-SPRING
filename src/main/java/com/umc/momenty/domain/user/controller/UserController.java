package com.umc.momenty.domain.user.controller;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.domain.user.exception.code.UserSuccessCode;
import com.umc.momenty.domain.user.service.command.UserCommandService;
import com.umc.momenty.domain.user.service.query.UserQueryService;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.oauth.dto.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping
    @Override
    public ApiResponse<UserResDTO.UserProfileDTO> getProfile(
            @AuthUser Long userId
    ){
        return ApiResponse.onSuccess(UserSuccessCode.USER_FOUND, userQueryService.getUserProfile(userId));
    }

    @PatchMapping
    @Override
    public ApiResponse<TokenDto> updateProfile(
            @AuthUser Long userId,
            @Valid @RequestBody UserReqDTO.UserProfileDTO userReqDTO
    ) {
        Optional<TokenDto>tokenOpt = userCommandService.updateProfile(userId, userReqDTO);
        return ApiResponse.onSuccess(UserSuccessCode.USER_UPDATED, tokenOpt.orElse(null));

    }

    @GetMapping("/user")
    @Override
    public ApiResponse<UserResDTO.UserDTO> getUser(
            @AuthUser Long userId
    ){
        return ApiResponse.onSuccess(UserSuccessCode.USER_DETAIL_FOUND, userQueryService.getUser(userId));
    }
}
