package com.umc.momenty.domain.pet.service.command;

import com.umc.momenty.domain.pet.converter.PetConverter;
import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.domain.pet.entity.Breed;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.BreedRepository;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetCommandServiceImpl implements PetCommandService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final BreedRepository breedRepository;

    public void createPetProfile(Long userId, PetReqDTO.PetProfileDTO petProfileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Breed breed = breedRepository.findById(petProfileDTO.breedId())
                .orElseThrow(() -> new PetException(PetErrorCode.BREED_NOT_FOUND));

        Pet pet = PetConverter.toPet(petProfileDTO, user, breed);

        petRepository.save(pet);
    }
}
