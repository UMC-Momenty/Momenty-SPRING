package com.umc.momenty.domain.pet.converter;

import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.domain.pet.dto.res.PetResDTO;
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

    public static PetResDTO.PetDTO toPetDTO(Pet pet){
        return PetResDTO.PetDTO.builder()
                .petId(pet.getId())
                .profileImageUrl(pet.getProfileImageUrl())
                .petName(pet.getPetName())
                .gender(pet.getGender())
                .birth(pet.getBirth())
                .species(pet.getSpecies())
                .breedName(pet.getBreed().getName())
                .intro(pet.getIntro())
                .build();
    }

    public static PetResDTO.MyPetDTO toMyPetDTO(Pet pet) {
        return PetResDTO.MyPetDTO.builder()
            .petId(pet.getId())
            .petName(pet.getPetName())
            .species(pet.getBreed().getSpecies())
            .breedName(pet.getBreed().getName())
            .profileImageUrl(pet.getProfileImageUrl())
            .build();
    }
}
