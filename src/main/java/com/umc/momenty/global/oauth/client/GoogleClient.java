package com.umc.momenty.global.oauth.client;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.oauth.dto.OAuthUserInfo;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class GoogleClient implements SocialClient {

    @Override
    public SocialProvider provider() {
        return SocialProvider.GOOGLE;
    }

    @Override
    public OAuthUserInfo getUserInfo(String idTokenString) {
        try {

            log.info("========== GOOGLE LOGIN START ==========");
            log.info("Received ID Token length: {}", idTokenString.length());
            log.info("ID Token (first 50 chars): {}",
                    idTokenString.substring(0, Math.min(50, idTokenString.length())));

            // 🔎 JWT payload 직접 디코딩해서 로그 출력
            String[] parts = idTokenString.split("\\.");
            if (parts.length == 3) {
                String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                log.info("Decoded JWT Payload: {}", payloadJson);
            } else {
                log.warn("Token format is invalid (not 3 parts)");
            }

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance()
            ).build();

            log.info("Verifying Google ID Token...");

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                log.error("GoogleIdTokenVerifier returned null (verification failed)");
                throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
            }

            log.info("Google ID Token verified successfully");

            GoogleIdToken.Payload payload = idToken.getPayload();

            log.info("Payload issuer: {}", payload.getIssuer());
            log.info("Payload audience: {}", payload.getAudience());
            log.info("Payload subject (sub): {}", payload.getSubject());
            log.info("Payload email: {}", payload.getEmail());
            log.info("Payload expirationTime: {}", payload.getExpirationTimeSeconds());
            log.info("CurrentTimeMillis: {}", System.currentTimeMillis() / 1000);

            String providerId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            log.info("Extracted providerId: {}", providerId);
            log.info("Extracted email: {}", email);
            log.info("Extracted name: {}", name);

            log.info("========== GOOGLE LOGIN SUCCESS ==========");

            return new OAuthUserInfo(providerId, email, name);

        } catch (Exception e) {
            log.error("========== GOOGLE LOGIN FAILED ==========");
            log.error("Exception class: {}", e.getClass().getName());
            log.error("Exception message: {}", e.getMessage());
            log.error("Stack trace:", e);
            throw new GeneralException(OAuthErrorCode.OAUTH_USERINFO_FAILED);
        }
    }
}
