package com.umc.momenty.global.oauth.dto;

public record OAuthLogin(
        String provider,
        String accessToken
) {}