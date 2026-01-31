package com.umc.momenty.domain.daily.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umc.momenty.domain.daily.entity.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {

	Optional<DailyQuestion> findFirstByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
