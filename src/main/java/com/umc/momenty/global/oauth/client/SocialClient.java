package com.umc.momenty.global.oauth.client;

import com.umc.momenty.global.oauth.dto.OAuthUserInfo;

public interface SocialClient {

    SocialProvider provider();

    OAuthUserInfo getUserInfo(String accessToken);
}
