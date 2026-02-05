package com.umc.momenty.domain.pet.dto.res;

import com.umc.momenty.domain.pet.enums.Species;

import lombok.Builder;

public class PetResDTO {

    @Builder
    public record PetInfoDTO(
            Long petId,
            String petName,
            String profileImageUrl
    ){}

    @Builder
    public record MyPetDTO(
            Long petId,
            String petName,
            Species species,
            String breedName,
            String profileImageUrl
    ){}
}
