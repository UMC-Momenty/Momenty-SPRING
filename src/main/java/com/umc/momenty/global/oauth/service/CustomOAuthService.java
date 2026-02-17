package com.umc.momenty.global.oauth.service;


import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.enums.AuthProvider;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.jwt.JwtUtil;
import com.umc.momenty.global.oauth.client.SocialClient;
import com.umc.momenty.global.oauth.client.SocialProvider;
import com.umc.momenty.global.oauth.dto.OAuthLogin;
import com.umc.momenty.global.oauth.dto.OAuthUserInfo;
import com.umc.momenty.global.oauth.dto.TokenDto;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtException;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomOAuthService {

    private final UserRepository userRepository;
    private final Map<SocialProvider, SocialClient> socialClients;
    private final JwtUtil jwtUtil;


    public CustomOAuthService(UserRepository userRepository, List<SocialClient> socialClients, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.socialClients = socialClients.stream().collect(Collectors.toMap(SocialClient::provider, Function.identity()));
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public TokenDto oAuthLogin(OAuthLogin oAuthLogin) {
        SocialProvider provider = SocialProvider.from(oAuthLogin.provider());
        AuthProvider authProvider = AuthProvider.valueOf(provider.name());
        SocialClient socialClient = socialClients.get(provider);

        OAuthUserInfo userInfo = socialClient.getUserInfo(oAuthLogin.accessToken());
        String email = userInfo.email();
        String socialId = userInfo.providerId();

        User user = userRepository.findByAuthProviderAndSocialId(authProvider, socialId)
                .orElseGet(() -> {
                    if (email != null && !email.isBlank() && userRepository.existsByEmail(email)) {
                        throw new GeneralException(OAuthErrorCode.OAUTH_EMAIL_ALREADY_USED);
                    }
                    return userRepository.save(
                            User.builder()
                                    .authProvider(authProvider)
                                    .socialId(socialId)
                                    .email(email)
                                    .username(
                                            userInfo.name() != null
                                                    ? userInfo.name()
                                                    : "User_" + UUID.randomUUID().toString().substring(0, 8)
                                    )
                                    .build()
                    );
                });
        String role = (user.getBirth() != null && user.getGender() != null)
                ? "ROLE_USER"
                : "ROLE_GUEST";

        log.info("[OAUTH_LOGIN] userId={}, provider={}, socialId={}, birth={}, gender={}, role={}",
                user.getId(), authProvider, user.getSocialId(),
                user.getBirth(), user.getGender(), role);

        return jwtUtil.generateTokens(user.getId(), user.getSocialId(), role);
    }

    @Transactional
    public TokenDto reissueToken(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new GeneralException(OAuthErrorCode.REFRESH_TOKEN_MISSING);
        }

        Claims claims;
        try {
            claims = jwtUtil.parseClaims(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new GeneralException(OAuthErrorCode.REFRESH_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new GeneralException(OAuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String tokenType = claims.get("tokenType", String.class);
        if (!"refresh".equals(tokenType)) {
            throw new GeneralException(OAuthErrorCode.INVALID_TOKEN_TYPE);
        }
        Number userIdNum = claims.get("userId", Number.class);
        Long userId = userIdNum.longValue();
        if (!userRepository.existsById(userId)) {
            throw new GeneralException(OAuthErrorCode.INVALID_REFRESH_TOKEN);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(OAuthErrorCode.INVALID_REFRESH_TOKEN));

        String role = (user.getBirth() != null && user.getGender() != null)
                ? "ROLE_USER"
                : "ROLE_GUEST";
        return jwtUtil.generateTokens(user.getId(), user.getSocialId(), role);
    }

    public void logout(String refreshToken) {

    }
}
