package com.umc.momenty.domain.schedule.dto.res;

import com.umc.momenty.domain.schedule.enums.ScheduleCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleResDTO {

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

    // 4. 특정 반려동물 일별 일정 조회 응답
    // 리스트 안의 개별 일정 객체
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyScheduleDTO {
        private Long scheduleId;
        private String title;         // 일정 제목
        private LocalDateTime startAt; // 시작 일시
        private String memo;          // 메모
        private ScheduleCategory category; // 카테고리
    }

    // 최종 응답 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyScheduleResponseDTO {
        private Long petId;
        private LocalDate date;
        private List<DailyScheduleDTO> schedules;
    }


    // 5. 일정 생성 성공 응답

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleIdResponseDTO {
        private Long scheduleId;
    }
}
