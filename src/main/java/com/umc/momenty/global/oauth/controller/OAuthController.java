package com.umc.momenty.global.oauth.controller;


import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.oauth.dto.OAuthLogin;
import com.umc.momenty.global.oauth.dto.TokenDto;
import com.umc.momenty.global.oauth.exception.code.OAuthSuccessCode;
import com.umc.momenty.global.oauth.service.CustomOAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final CustomOAuthService customOAuthService;

    @PostMapping("/login")
    public ApiResponse<TokenDto> oAuthLogin(@RequestBody OAuthLogin oAuthLogin) {
        TokenDto tokenDto = customOAuthService.oAuthLogin(oAuthLogin);
        return ApiResponse.onSuccess(OAuthSuccessCode.OAUTH_LOGIN_SUCCESS, tokenDto);
    }

    @PostMapping("/reissue")
    public ApiResponse<TokenDto> reissueToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("X-Refresh-Token");
        TokenDto tokenDto = customOAuthService.reissueToken(refreshToken);
        return ApiResponse.onSuccess(OAuthSuccessCode.OAUTH_REISSUE_SUCCESS, tokenDto);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("X-Refresh-Token");
        customOAuthService.logout(refreshToken);
        return ApiResponse.onSuccess(OAuthSuccessCode.OAUTH_LOGOUT_SUCCESS, null);
    }

}
