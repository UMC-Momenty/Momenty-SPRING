package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.FaqResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FaqControllerDocs {

    @Operation(summary = "FAQ 상세 조회 API", description = "FAQ ID를 기반으로 FAQ 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 FAQ")
    })
    ApiResponse<FaqResDTO.FaqDTO> getFaq(@PathVariable Long faqId);

    @Operation(summary = "FAQ 리스트 조회 API",
            description = "전체 FAQ를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<List<FaqResDTO.FaqListDTO>> getAllFaq();
}
