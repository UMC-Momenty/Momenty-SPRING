package com.umc.momenty.domain.pet.service.command;

import com.umc.momenty.domain.pet.dto.req.PetReqDTO;

public interface PetCommandService {

    void createPetProfile(Long userId, PetReqDTO.PetProfileDTO petProfileDTO);
}
