package com.umc.momenty.domain.daily.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umc.momenty.domain.daily.entity.DailyAnswer;
import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.entity.User;

public interface DailyAnswerRepository extends JpaRepository<DailyAnswer, Long> {
	@Query("""
    select da
    from DailyAnswer da
    join fetch da.dailyQuestion dq
    where da.user.id = :userId
    and da.createdAt between :start and :end
""")
	List<DailyAnswer> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

	boolean existsByUserAndPetAndDailyQuestion(User user, Pet pet, DailyQuestion dailyQuestion);

	Optional<DailyAnswer> findByUserIdAndDailyQuestion(Long userId, DailyQuestion dailyQuestion);
}
