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


    @Operation(summary = "일정 등록용 내 펫 목록 조회 API", description = "일정 등록 화면에서 선택할 사용자의 펫 목록을 조회합니다.")
    ApiResponse<ScheduleResDTO.MyPetsResponseDTO> getMyPets(@Parameter(description = "사용자 ID") Long userId);

    @Operation(summary = "전체 캘린더 조회 API", description = "해당 월의 모든 반려동물 일정을 조회합니다.")
    ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarAll(@Parameter(description = "사용자 ID") Long userId, @Parameter(description = "조회할 년도 (YYYY)") int year, @Parameter(description = "조회할 월 (MM)") @Min(1) @Max(12) int month);

    @Operation(summary = "특정 펫 캘린더 조회 API", description = "해당 월의 특정 반려동물 일정만 필터링하여 조회합니다.")
    ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarByPet(@Parameter(description = "사용자 ID") Long userId, @Parameter(description = "반려동물 ID") Long petId, @Parameter(description = "조회할 년도 (YYYY)") int year, @Parameter(description = "조회할 월 (MM)") @Min(1) @Max(12) int month);

    @Operation(summary = "특정 반려동물 일별 일정 조회 API", description = "선택한 날짜에 해당하는 특정 반려동물의 상세 일정 목록을 조회합니다.")
    ApiResponse<ScheduleResDTO.DailyScheduleResponseDTO> getDailyScheduleByPet(@Parameter(description = "반려동물 ID") Long petId, @Parameter(description = "조회할 날짜 (YYYY-MM-DD)") LocalDate date);

    @Operation(summary = "반려동물 일정(알림) 생성 API", description = "반려동물의 일정/알림을 생성합니다. (일회성/반복성)")
    ApiResponse<ScheduleResDTO.ScheduleIdResponseDTO> registerSchedule(@Parameter(description = "반려동물 ID") Long petId, @RequestBody(description = "일정 생성 요청 정보") @Valid ScheduleReqDTO.ScheduleCreateDTO request);

    // [NEW] 알림 목록 조회
    @Operation(summary = "알림 목록 조회 API", description = "해당 펫의 모든 알림(반복/일회성) 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 반려동물")
    })
    ApiResponse<ScheduleResDTO.AlarmListDTO> getAlarmList(@Parameter(description = "반려동물 ID") Long petId);

    // [NEW] 알림 ON/OFF 토글
    @Operation(summary = "알림 상태 변경(ON/OFF) API", description = "특정 알림의 활성화 여부를 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 일정")
    })
    ApiResponse<ScheduleResDTO.AlarmStatusResponseDTO> toggleAlarmStatus(
            @Parameter(description = "일정 ID") Long scheduleId,
            @RequestBody(description = "변경할 알림 상태") ScheduleReqDTO.AlarmStatusDTO request
    );
}
