package com.umc.momenty.domain.schedule.entity;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.schedule.enums.ScheduleCategory;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기존 DB 컬럼명이 content라면 유지하고, 자바 필드명만 title로 통일
    @Column(name = "content", nullable = false, length = 50)
    private String title;

    // 알람/반복 테이블 단순화 시 Schedule에 합칠 수 있는 필드들
    @Column(name = "memo", length = 500)
    private String memo;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    // NULL이면 반복 X, 값이 있으면 반복 O (예: "MON,WED,FRI")
    @Column(name = "repeat_days", length = 30)
    private String repeatDays;

    // 알람 시간(시간만). 필요 없다면 startDateTime의 time을 사용하도록 서비스에서 처리
    @Column(name = "alarm_time")
    private LocalTime alarmTime;

    @Column(name = "is_alarm_enabled", nullable = false)
    @Builder.Default
    private boolean isAlarmEnabled = true;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleCategory category;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // 알림 상태 변경 메서드 (Dirty Checking용)
    public void updateAlarmStatus(boolean isEnabled) {
        this.isAlarmEnabled = isEnabled;
    }
}
