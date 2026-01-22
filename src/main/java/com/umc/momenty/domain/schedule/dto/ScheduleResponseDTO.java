package com.umc.momenty.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ScheduleResponseDTO {

    // 1. 유저 반려동물 조회 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetProfileDTO {
        private Long petId;
        private String profile;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPetsResponseDTO {
        private List<PetProfileDTO> pets;
    }

    // 2 & 3. 캘린더 날짜별 일정 개수 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DayCountDTO {
        private LocalDate date;
        private Long count;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalendarResponseDTO {
        private Long petId; // 전체 조회면 null
        private int year;
        private int month;
        private List<DayCountDTO> days;
    }
}
