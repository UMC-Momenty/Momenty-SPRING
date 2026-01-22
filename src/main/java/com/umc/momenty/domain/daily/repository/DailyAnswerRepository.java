package com.umc.momenty.domain.daily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umc.momenty.domain.daily.entity.DailyAnswer;

public interface DailyAnswerRepository extends JpaRepository<DailyAnswer, Long> {
}
