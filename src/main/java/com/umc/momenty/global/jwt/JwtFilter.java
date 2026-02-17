package com.umc.momenty.global.jwt;

import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/api/oauth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveAccessToken(request);
        if(accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try{
            Claims claims = jwtUtil.parseClaims(accessToken);
            String tokenType = claims.get("tokenType", String.class);
            if (!"access".equals(tokenType)) {
                SecurityContextHolder.clearContext();
                ErrorResponse.sendErrorResponse(response, OAuthErrorCode.INVALID_TOKEN_TYPE);
                return;
            }
            Number userIdNum = claims.get("userId", Number.class);
            Long userId = userIdNum.longValue();
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> authorities =
                    (role == null || role.isBlank())
                            ? List.of()
                            : List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
            log.info("[JWT] path={}, userId={}, role={}", request.getRequestURI(), userId, role);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            ErrorResponse.sendErrorResponse(response, OAuthErrorCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            ErrorResponse.sendErrorResponse(response, OAuthErrorCode.INVALID_TOKEN);
        }
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        }
        String token = bearerToken.substring(7);
        return token.isBlank() ? null : token;
    }

}
