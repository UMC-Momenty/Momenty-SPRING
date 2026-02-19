package com.umc.momenty.domain.user.dto.res;

import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import com.umc.momenty.domain.user.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UserResDTO {

    @Builder
    public record UserProfileDTO(
            Long userId,
            String username,
            String profileUrl,
            List<PetResDTO.PetInfoDTO> pets
    ){}

    @Builder
    public record UserDTO(
            Long userId,
            String profileUrl,
            String username,
            Gender gender,
            LocalDate birth,
            LocalTime questTime
    ){}
}
