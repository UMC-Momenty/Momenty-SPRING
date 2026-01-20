package com.umc.momenty.domain.pet.converter;

import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.domain.pet.entity.Breed;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.entity.User;

public class PetConverter {

    public static Pet toPet(PetReqDTO.PetProfileDTO dto, User user, Breed breed) {
        return Pet.builder()
                .profileImageUrl(dto.profileImageUrl())
                .petName(dto.petName())
                .gender(dto.gender())
                .birth(dto.birth())
                .species(dto.species())
                .intro(dto.intro())
                .user(user)
                .breed(breed)
                .build();
    }
}
