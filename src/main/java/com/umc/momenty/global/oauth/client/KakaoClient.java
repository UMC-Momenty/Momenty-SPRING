package com.umc.momenty.global.oauth.client;

import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.oauth.dto.OAuthUserInfo;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;


import java.util.Map;

@Component
@Slf4j
public class KakaoClient implements SocialClient {

    private final RestClient restClient;

    public KakaoClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build();
    }

    @Override
    public SocialProvider provider() {
        return SocialProvider.KAKAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        try{

            var uriSpec = restClient.get().uri("/v2/user/me");
            var headersSpec = uriSpec.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            var responseSpec = headersSpec.retrieve();
            Map<String, Object> response = responseSpec.body(Map.class);
            log.info("[KAKAO RAW USER INFO RESPONSE] {}", response);

            if (response == null || response.get("id") == null) {
                log.info("Kakao user info response is invalid: {}", response);
                throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
            }
            String providerId = String.valueOf(response.get("id"));

            String email = null;
            String name = null;

            Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");

                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    name = (String) profile.get("nickname");
                }
            }
            return new OAuthUserInfo( providerId, email, name);

        } catch (Exception e) {
            log.info("Kakao user info response is invalid: {}", e.getMessage());
            throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
        }


    }
}
