package com.umc.momenty.domain.user.dto.res;

import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import lombok.Builder;

import java.util.List;

public class UserResDTO {

    @Builder
    public record UserProfileDTO(
            Long userId,
            String username,
            String profileUrl,
            List<PetResDTO.PetInfoDTO> pets
    ){}
}
