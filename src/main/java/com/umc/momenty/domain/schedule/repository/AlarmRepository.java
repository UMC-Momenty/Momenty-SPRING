package com.umc.momenty.domain.schedule.repository;

import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.schedule.entity.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    // 특정 스케줄에 연결된 알람 조회
    Optional<Alarm> findBySchedule(Schedule schedule);
}
