package com.umc.momenty.global.apiPayload.exception.handler;

import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.code.GeneralErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(GeneralException ex) {
        BaseErrorCode code = ex.getCode();

        log.warn("[ GeneralException ] code={}, message={}", code.getCode(), code.getMessage());

        return ResponseEntity
                .status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<Void>> handleAllException(Exception ex) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        log.error("[ InternalServerError ]", ex);

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        BaseErrorCode code = GeneralErrorCode.VALID_FAILED;

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, errors));
    }
}