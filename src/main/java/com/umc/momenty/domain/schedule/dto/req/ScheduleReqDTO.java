package com.umc.momenty.domain.schedule.dto.req;

import com.umc.momenty.domain.schedule.enums.ScheduleCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.util.List;

public class ScheduleReqDTO {

    @Getter
    public static class ScheduleCreateDTO {
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotNull(message = "카테고리를 선택해주세요.")
        private ScheduleCategory category;

        private String memo;

        @NotBlank(message = "시간을 입력해주세요.")
        private String time; // "14:00"

        // "ONE_TIME" 또는 "REPEAT"
        @NotBlank(message = "일정 타입을 입력해주세요.")
        private String type;

        // 일회성일 때 필수 (예: "2026-02-04")
        private String date;

        // 반복성일 때 필수 (예: ["MONDAY", "WEDNESDAY"])
        private List<String> repeatDays;

        private Integer durationMinutes;

        private Boolean isAlarmEnabled; // 기본값 true (null이면 서비스에서 처리)
    }

    // [NEW] 알림 ON/OFF 토글용 DTO
    @Getter
    public static class AlarmStatusDTO {
        @NotNull
        private Boolean isAlarmEnabled;
    }
}
