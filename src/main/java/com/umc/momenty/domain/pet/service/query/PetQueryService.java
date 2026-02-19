package com.umc.momenty.domain.pet.service.query;

import com.umc.momenty.domain.pet.dto.res.PetResDTO;

public interface PetQueryService {
    PetResDTO.PetDTO getPet(Long userId, Long petId);
}
