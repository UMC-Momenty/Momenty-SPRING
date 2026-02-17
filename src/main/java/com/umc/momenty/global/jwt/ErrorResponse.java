package com.umc.momenty.global.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.momenty.global.oauth.exception.code.OAuthErrorCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorResponse {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendErrorResponse(HttpServletResponse response, OAuthErrorCode authErrorCode) throws IOException {
        response.setStatus(authErrorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("isSuccess", false);
        errorResponse.put("code", authErrorCode.getCode());
        errorResponse.put("message", authErrorCode.getMessage());

        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
