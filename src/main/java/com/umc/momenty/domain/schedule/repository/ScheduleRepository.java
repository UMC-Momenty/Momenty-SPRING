package com.umc.momenty.domain.schedule.repository;

import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.user = :user AND s.startDateTime BETWEEN :start AND :end AND s.deletedAt IS NULL")
    List<Schedule> findAllByUserAndMonth(@Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT s FROM Schedule s WHERE s.pet.id = :petId AND s.user = :user AND s.startDateTime BETWEEN :start AND :end AND s.deletedAt IS NULL")
    List<Schedule> findAllByPetAndMonth(@Param("petId") Long petId, @Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT s FROM Schedule s WHERE s.pet.id = :petId AND s.startDateTime BETWEEN :start AND :end AND s.deletedAt IS NULL")
    List<Schedule> findAllByPetAndDate(@Param("petId") Long petId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // [NEW] 알림 시간이 설정된 일정만 조회 (삭제된 것 제외, 알림 시간순 정렬)
    @Query("SELECT s FROM Schedule s WHERE s.pet.id = :petId AND s.alarmTime IS NOT NULL AND s.deletedAt IS NULL ORDER BY s.alarmTime ASC")
    List<Schedule> findAllAlarmsByPet(@Param("petId") Long petId);
}
