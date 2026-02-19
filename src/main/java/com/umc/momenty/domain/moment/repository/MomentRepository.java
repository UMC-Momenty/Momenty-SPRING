package com.umc.momenty.domain.moment.repository;

import com.umc.momenty.domain.moment.entity.Moment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MomentRepository extends JpaRepository<Moment, Long> {
    Page<Moment> findAllByUserIdAndPetId(Long userId, Long petId, Pageable pageable);
    Optional<Moment> findByIdAndUserIdAndPetId(Long momentId, Long userId, Long petId);

	long countByUserIdAndPetId(Long userId, Long petId);
}
