package com.umc.momenty.domain.schedule.dto.req;

import com.umc.momenty.domain.schedule.enums.ScheduleCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ScheduleReqDTO {

    @Getter
    public static class ScheduleCreateDTO {
        @NotBlank(message = "제목(내용)을 입력해주세요.")
        private String title;    // 일정 제목 (API 명세: title)

        private String type;     // 반복 여부 ("REPEAT", "ONE_TIME")

        private String date;     // 날짜 ("2026-01-02", type이 ONE_TIME일 때 사용)

        private Integer dayOfWeek; // 요일 (1:월 ~ 7:일, type이 REPEAT일 때 사용)

        @NotBlank(message = "시간을 입력해주세요.")
        private String time;     // 시간 ("14:00")

        private Integer durationMinutes; // 소요 시간 (분)

        private String memo;     // 메모

        @NotNull(message = "카테고리를 선택해주세요.")
        private ScheduleCategory category; // 카테고리 (WALK, MEAL 등)
    }
}
