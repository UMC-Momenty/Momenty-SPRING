package com.umc.momenty.global.oauth.controller;

import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.oauth.dto.OAuthLogin;
import com.umc.momenty.global.oauth.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestHeader;

public interface OAuthControllerDocs {

    @Operation(
            summary = "소셜 로그인 API",
            description = "소셜 로그인(카카오/네이버/구글 등) 액세스 토큰을 받아 서버에서 사용자 정보를 조회한 뒤, 서비스용 JWT(Access/Refresh)를 발급합니다.\n\n"
                    + "- 최초 로그인 시 사용자가 없으면 자동으로 생성됩니다.\n"
                    + "- 프로필 정보(birth, gender)가 미완료된 경우 ROLE_GUEST로, 완료된 경우 ROLE_USER로 토큰이 발급될 수 있습니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 (provider 누락/지원하지 않는 provider 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "동일 이메일로 가입된 다른 소셜 계정 존재"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "502", description = "OAuth 유저 정보 조회 실패")
    })
    ApiResponse<TokenDto> oAuthLogin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "소셜 로그인 요청\n\n"
                            + "**요청 예시**\n"
                            + "{\n"
                            + "  \"provider\": \"KAKAO\",\n"
                            + "  \"accessToken\": \"<kakao_access_token>\"\n"
                            + "}\n",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OAuthLogin.class))
            )
            OAuthLogin oAuthLogin
    );

    @Operation(
            summary = "토큰 재발급 API",
            description = "Refresh Token을 이용해 새로운 Access/Refresh 토큰을 재발급합니다.\n\n"
                    + "- Refresh Token은 요청 헤더 `X-Refresh-Token`으로 전달합니다.\n"
                    + "- Refresh Token이 만료/위조/유효하지 않으면 401로 실패합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패 (리프레시 토큰 누락/만료/유효하지 않음/토큰 타입 오류)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 또는 토큰 사용자 정보 불일치")
    })
    ApiResponse<TokenDto> reissueToken(
            @RequestHeader("X-Refresh-Token")
            @Parameter(
                    description = "리프레시 토큰 (헤더)",
                    required = true,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            String xRefreshToken
    );
}
