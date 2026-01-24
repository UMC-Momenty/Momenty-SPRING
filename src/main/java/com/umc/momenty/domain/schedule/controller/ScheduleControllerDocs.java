package com.umc.momenty.domain.schedule.controller;

import com.umc.momenty.domain.schedule.dto.req.ScheduleReqDTO;
import com.umc.momenty.domain.schedule.dto.res.ScheduleResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public interface ScheduleControllerDocs {
    //내 펫 목록 조회
    @Operation(
            summary = "일정 등록용 내 펫 목록 조회 API",
            description = "일정 등록 화면에서 선택할 사용자의 펫 목록을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<ScheduleResDTO.MyPetsResponseDTO> getMyPets(
            @Parameter(description = "사용자 ID") Long userId
    );
    //전체 캘린더 조회
    @Operation(
            summary = "전체 캘린더 조회 API",
            description = "해당 월의 모든 반려동물 일정을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 날짜 요청 (month는 1~12 사이여야 함)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarAll(
            @Parameter(description = "사용자 ID") Long userId,
            @Parameter(description = "조회할 년도 (YYYY)") int year,
            @Parameter(description = "조회할 월 (MM) * 1~12 사이 값 입력") @Min(1) @Max(12) int month
    );
    //특정 펫 캘린더 조회
    @Operation(
            summary = "특정 펫 캘린더 조회 API",
            description = "해당 월의 특정 반려동물 일정만 필터링하여 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 날짜 요청 (month는 1~12 사이여야 함)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 또는 반려동물")
    })
    ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarByPet(
            @Parameter(description = "사용자 ID") Long userId,
            @Parameter(description = "반려동물 ID") Long petId,
            @Parameter(description = "조회할 년도 (YYYY)") int year,
            @Parameter(description = "조회할 월 (MM) * 1~12 사이 값 입력") @Min(1) @Max(12) int month
    );
    // 4. 특정 반려동물 일별 일정 조회
    @Operation(
            summary = "특정 반려동물 일별 일정 조회 API",
            description = "선택한 날짜(YYYY-MM-DD)에 해당하는 특정 반려동물의 상세 일정 목록을 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 반려동물")
    })
    ApiResponse<ScheduleResDTO.DailyScheduleResponseDTO> getDailyScheduleByPet(
            @Parameter(description = "반려동물 ID") Long petId,
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD)") LocalDate date
    );

    // 5. 반려동물 일정 생성
    @Operation(
            summary = "반려동물 일정 생성 API",
            description = "특정 반려동물의 새로운 일정을 등록합니다. (반복 일정 또는 단일 일정)"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "일정 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 반려동물")
    })
    ApiResponse<ScheduleResDTO.ScheduleIdResponseDTO> registerSchedule(
            @Parameter(description = "반려동물 ID") Long petId,
            @RequestBody(description = "일정 생성 요청 정보") @Valid ScheduleReqDTO.ScheduleCreateDTO request
    );
}
