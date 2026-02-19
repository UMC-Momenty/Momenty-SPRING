package com.umc.momenty.domain.pet.service.query;

import com.umc.momenty.domain.pet.converter.PetConverter;
import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetQueryServiceImpl implements PetQueryService {

    private final PetRepository petRepository;

    @Override
    public PetResDTO.PetDTO getPet(Long userId, Long petId){

        Pet pet = petRepository.findByUserIdAndId(userId, petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        return PetConverter.toPetDTO(pet);
    }
}
