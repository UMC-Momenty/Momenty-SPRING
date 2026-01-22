package com.umc.momenty.domain.schedule.controller;

import com.umc.momenty.domain.schedule.dto.ScheduleResponseDTO;
import com.umc.momenty.domain.schedule.service.ScheduleService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ApiResponse<ScheduleResponseDTO.MyPetsResponseDTO> getMyPets() {
        Long userId = 1L;
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, scheduleService.getMyPets(userId));
    }

    @GetMapping("/calendar")
    public ApiResponse<ScheduleResponseDTO.CalendarResponseDTO> getCalendarAll(
            @RequestParam int year,
            @RequestParam int month) {
        Long userId = 1L;
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, scheduleService.getCalendarAll(userId, year, month));
    }

    @GetMapping("/{petId}/calendar")
    public ApiResponse<ScheduleResponseDTO.CalendarResponseDTO> getCalendarByPet(
            @PathVariable Long petId,
            @RequestParam int year,
            @RequestParam int month) {
        Long userId = 1L;
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, scheduleService.getCalendarByPet(userId, petId, year, month));
    }
}
