package com.umc.momenty.global.oauth.client;

import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;

public enum SocialProvider {
    KAKAO, NAVER, GOOGLE;

    public static SocialProvider from (String provider) {
        if (provider == null) {
            throw new GeneralException(OAuthErrorCode.OAUTH_PROVIDER_REQUIRED);
        }
        try {
            return SocialProvider.valueOf(provider.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeneralException(OAuthErrorCode.OAUTH_PROVIDER_UNSUPPORTED);
        }
    }

}
