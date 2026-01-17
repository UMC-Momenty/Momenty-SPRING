package com.umc.momenty.domain.daily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umc.momenty.domain.daily.entity.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {
}
