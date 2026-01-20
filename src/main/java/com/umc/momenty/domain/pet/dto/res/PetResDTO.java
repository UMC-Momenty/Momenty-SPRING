package com.umc.momenty.domain.pet.dto.res;

import lombok.Builder;

public class PetResDTO {

    @Builder
    public record PetInfoDTO(
            Long petId,
            String petName,
            String profileImageUrl
    ){}
}
