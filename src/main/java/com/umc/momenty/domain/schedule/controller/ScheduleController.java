package com.umc.momenty.domain.schedule.controller;

import com.umc.momenty.domain.schedule.dto.req.ScheduleReqDTO;
import com.umc.momenty.domain.schedule.dto.res.ScheduleResDTO;
import com.umc.momenty.domain.schedule.exception.code.ScheduleSuccessCode;
import com.umc.momenty.domain.schedule.service.ScheduleService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ScheduleController implements ScheduleControllerDocs {

    private final ScheduleService scheduleService;

    // 1. 내 펫 목록 조회
    @GetMapping("/users/{userId}/schedules/pets")
    @Override
    public ApiResponse<ScheduleResDTO.MyPetsResponseDTO> getMyPets(@PathVariable Long userId) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_FOUND, scheduleService.getMyPets(userId));
    }

    // 2. 전체 캘린더 조회
    @GetMapping("/users/{userId}/schedules/calendar")
    @Override
    public ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarAll(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam @Min(1) @Max(12) int month) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_FOUND, scheduleService.getCalendarAll(userId, year, month));
    }

    // 3. 특정 펫 캘린더 조회
    @GetMapping("/users/{userId}/schedules/pets/{petId}/calendar")
    @Override
    public ApiResponse<ScheduleResDTO.CalendarResponseDTO> getCalendarByPet(
            @PathVariable Long userId,
            @PathVariable Long petId,
            @RequestParam int year,
            @RequestParam @Min(1) @Max(12) int month) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_FOUND, scheduleService.getCalendarByPet(userId, petId, year, month));
    }

    // 4. 특정 반려동물 일별 일정 조회
    @GetMapping("/pets/{petId}/schedules")
    @Override
    public ApiResponse<ScheduleResDTO.DailyScheduleResponseDTO> getDailyScheduleByPet(
            @PathVariable Long petId,
            @RequestParam LocalDate date) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_FOUND, scheduleService.getDailyScheduleByPet(petId, date));
    }

    // 5. 반려동물 일정 생성 (업데이트됨)
    @PostMapping("/pets/{petId}/schedules")
    @Override
    public ApiResponse<ScheduleResDTO.ScheduleIdResponseDTO> registerSchedule(
            @PathVariable Long petId,
            @RequestBody @Valid ScheduleReqDTO.ScheduleCreateDTO request) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_CREATED, scheduleService.registerSchedule(petId, request));
    }

    // [NEW] 6. 알림 목록 조회
    @GetMapping("/pets/{petId}/alarms")
    @Override
    public ApiResponse<ScheduleResDTO.AlarmListDTO> getAlarmList(@PathVariable Long petId) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_FOUND, scheduleService.getAlarmList(petId));
    }

    // [NEW] 7. 알림 ON/OFF 토글
    @PatchMapping("/schedules/{scheduleId}/alarm-status")
    @Override
    public ApiResponse<ScheduleResDTO.AlarmStatusResponseDTO> toggleAlarmStatus(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleReqDTO.AlarmStatusDTO request) {
        return ApiResponse.onSuccess(ScheduleSuccessCode.SCHEDULE_UPDATED, scheduleService.toggleAlarmStatus(scheduleId, request.getIsAlarmEnabled()));
    }
}
