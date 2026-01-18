package com.umc.momenty.domain.pet.dto.req;

import com.umc.momenty.domain.pet.enums.PetGender;
import com.umc.momenty.domain.pet.enums.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public class PetReqDTO {

    @Builder
    public record PetProfileDTO(
            String profileImageUrl,

            @NotBlank(message = "반려동물 이름은 필수입니다.")
            String petName,

            @NotNull(message = "성별은 필수입니다.")
            PetGender gender,

            @NotNull(message = "생일은 필수입니다.")
            LocalDate birth,

            @NotNull(message = "종은 필수입니다.")
            Species species,

            @NotNull(message = "품종은 필수입니다.")
            Long breedId,

            @Size(max = 500, message = "소개글은 500자 이내여야 합니다.")
            String intro
    ){}
}
