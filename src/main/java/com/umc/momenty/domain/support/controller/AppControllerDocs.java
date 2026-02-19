package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.AppResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AppControllerDocs {

    @Operation(summary = "App 상세 조회 API", description = "App ID를 기반으로 App 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 App 정보")
    })
    ApiResponse<AppResDTO.AppDTO> getApp(@PathVariable Long appId);

    @Operation(summary = "App 리스트 조회 API",
            description = "전체 App 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<AppResDTO.AppListDTO>> getAllApp();
}
