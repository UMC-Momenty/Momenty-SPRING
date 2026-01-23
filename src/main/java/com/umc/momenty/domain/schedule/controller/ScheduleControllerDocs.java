package com.umc.momenty.domain.schedule.controller;

import com.umc.momenty.domain.schedule.dto.ScheduleResponseDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ScheduleControllerDocs {

    @Operation(
            summary = "일정 등록용 내 펫 목록 조회 API",
            description = "일정 등록 화면에서 선택할 사용자의 펫 목록을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<ScheduleResponseDTO.MyPetsResponseDTO> getMyPets(
            @Parameter(description = "사용자 ID") Long userId
    );

    @Operation(
            summary = "전체 캘린더 조회 API",
            description = "해당 월의 모든 반려동물 일정을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 날짜 요청 (month는 1~12 사이여야 함)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<ScheduleResponseDTO.CalendarResponseDTO> getCalendarAll(
            @Parameter(description = "사용자 ID") Long userId,
            @Parameter(description = "조회할 년도 (YYYY)") int year,
            @Parameter(description = "조회할 월 (MM) * 1~12 사이 값 입력") @Min(1) @Max(12) int month
    );

    @Operation(
            summary = "특정 펫 캘린더 조회 API",
            description = "해당 월의 특정 반려동물 일정만 필터링하여 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 날짜 요청 (month는 1~12 사이여야 함)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 또는 반려동물")
    })
    ApiResponse<ScheduleResponseDTO.CalendarResponseDTO> getCalendarByPet(
            @Parameter(description = "사용자 ID") Long userId,
            @Parameter(description = "반려동물 ID") Long petId,
            @Parameter(description = "조회할 년도 (YYYY)") int year,
            @Parameter(description = "조회할 월 (MM) * 1~12 사이 값 입력") @Min(1) @Max(12) int month
    );
}
