package com.umc.momenty.global.oauth.dto;

public record OAuthLogin(
        String provider, // 구글 네이버 카카오
        String accessToken  // 카카오/네이버: 소셜 accessToken, 구글: idToken
) {}