package com.umc.momenty.domain.pet.repository;

import com.umc.momenty.domain.pet.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
