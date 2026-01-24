package com.umc.momenty.domain.pet.repository;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUser(User user);
}


