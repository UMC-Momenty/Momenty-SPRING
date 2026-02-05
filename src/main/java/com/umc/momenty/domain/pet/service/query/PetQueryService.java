package com.umc.momenty.domain.pet.service.query;

import java.util.List;

import com.umc.momenty.domain.pet.dto.res.PetResDTO;

public interface PetQueryService {
	List<PetResDTO.MyPetDTO> getMyPetList(Long userId);
}
