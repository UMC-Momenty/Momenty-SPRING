package com.umc.momenty.domain.pet.dto.res;

import com.umc.momenty.domain.pet.enums.PetGender;
import com.umc.momenty.domain.pet.enums.Species;
import lombok.Builder;

import java.time.LocalDate;

public class PetResDTO {

    @Builder
    public record PetInfoDTO(
            Long petId,
            String petName,
            String profileImageUrl
    ){}

    @Builder
    public record PetDTO(
            Long petId,
            String profileImageUrl,
            String petName,
            PetGender gender,
            LocalDate birth,

            Species species,
            String breedName,

            String intro
    ){}
}
