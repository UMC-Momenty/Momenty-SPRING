package com.umc.momenty.global.oauth.client;

import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.oauth.dto.OAuthUserInfo;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@Slf4j
public class NaverClient implements SocialClient {

    private final RestClient restClient;

    public NaverClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://openapi.naver.com")
                .build();
    }

    @Override
    public SocialProvider provider() {
        return SocialProvider.NAVER;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        try {
            Map<String, Object> body = restClient.get()
                    .uri("/v1/nid/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .body(Map.class);

            log.info("[NAVER RAW USER INFO RESPONSE] {}", body);

            if (body == null) {
                log.error("Naver response body is null");
                throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
            }

            if (!"00".equals(body.get("resultcode"))) {
                log.error("Naver resultcode invalid: {}", body.get("resultcode"));
                throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
            }

            Map<String, Object> response = (Map<String, Object>) body.get("response");

            if (response == null) {
                log.error("Naver response field is null");
                throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
            }

            String providerId = (String) response.get("id");
            String email = (String) response.get("email");
            String name = (String) response.get("name");

            log.info("[NAVER PARSED USER INFO] id={}, email={}, name={}",
                    providerId, email, name);

            return new OAuthUserInfo(providerId, email, name);

        } catch (Exception e) {
            log.error("Naver user info parsing failed", e);
            throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
        }
    }
}
