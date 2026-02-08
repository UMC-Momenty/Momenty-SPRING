package com.umc.momenty.domain.schedule.dto.res;

import com.umc.momenty.domain.schedule.enums.ScheduleCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        private Long petId;
        private int year;
        private int month;
        private List<DayCountDTO> days;
    }

    // 4. 특정 반려동물 일별 일정 조회 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyScheduleDTO {
        private Long scheduleId;
        private String title;
        private LocalDateTime startAt;
        private String memo;
        private ScheduleCategory category;
    }

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

    // [NEW] 6. 알림 목록 조회용 (리스트 아이템)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmDTO {
        private Long scheduleId;
        private String title;
        private ScheduleCategory category;
        private List<String> repeatDays; // ["MONDAY", "WEDNESDAY"]
        private LocalDate date;          // 일회성이면 날짜 표시
        private LocalTime alarmTime;
        private boolean isOneTime;       // 일회성 여부
        private boolean isAlarmEnabled;  // 스위치 상태
        private Integer durationMinutes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmListDTO {
        private Long petId;
        private List<AlarmDTO> alarms;
    }

    // [NEW] 7. 알림 상태 변경 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmStatusResponseDTO {
        private Long scheduleId;
        private boolean isAlarmEnabled;
    }
}
