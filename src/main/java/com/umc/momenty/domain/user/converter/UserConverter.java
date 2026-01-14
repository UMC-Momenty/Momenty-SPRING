package com.umc.momenty.domain.user.converter;

import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import com.umc.momenty.domain.user.dto.res.UserResDTO;
import com.umc.momenty.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserResDTO.UserProfileDTO toUserProfileDTO(
            User user
    ) {
        List<PetResDTO.PetInfoDTO> pets = user.getPets().stream()
                        .map(pet -> PetResDTO.PetInfoDTO.builder()
                                .petId(pet.getId())
                                .petName(pet.getPetName())
                                .profileImageUrl(pet.getProfileImageUrl())
                                .build())
                        .collect(Collectors.toList());

        return UserResDTO.UserProfileDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .profileUrl(user.getProfileUrl())
                .pets(pets)
                .build();
    }
}
