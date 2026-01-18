package com.umc.momenty.domain.pet.service.command;

import com.umc.momenty.domain.pet.converter.PetConverter;
import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.domain.pet.entity.Breed;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.validator.PetValidator;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetCommandServiceImpl implements PetCommandService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetValidator petValidator;

    public void createPetProfile(Long userId, PetReqDTO.PetProfileDTO petProfileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Breed breed = petValidator.validateBreed(petProfileDTO.breedId());
        petValidator.validateBreedSpecies(breed, petProfileDTO.species());

        Pet pet = PetConverter.toPet(petProfileDTO, user, breed);

        petRepository.save(pet);
    }

    public void updatePetProfile(Long userId, Long petId, PetReqDTO.PetUpdateDTO petUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findById(petId)
                        .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        petValidator.validatePetOwner(pet, user);

        Optional.ofNullable(petUpdateDTO.petName())
                .filter(petName -> !petName.isBlank())
                .ifPresent(pet::updatePetName);

        Optional.ofNullable(petUpdateDTO.gender())
                .ifPresent(pet::updateGender);

        Optional.ofNullable(petUpdateDTO.birth())
                .ifPresent(pet::updateBirth);

        Optional.ofNullable(petUpdateDTO.species())
                .ifPresent(pet::updateSpecies);

        Optional.ofNullable(petUpdateDTO.breedId())
                .ifPresent(breedId -> {
                    Breed breed = petValidator.validateBreed(breedId);
                    petValidator.validateBreedSpecies(breed, pet.getSpecies());
                    pet.updateBreed(breed);
                });

        Optional.ofNullable(petUpdateDTO.intro())
                .ifPresent(intro -> {
                    if (intro.isBlank()) pet.updateIntro(null);
                    else pet.updateIntro(intro);
                });

        Optional.ofNullable(petUpdateDTO.profileImageUrl())
                .ifPresent(profileImageUrl -> {
                    if (profileImageUrl.isBlank()) pet.updateProfileImageUrl(null);
                    else pet.updateProfileImageUrl(profileImageUrl);
                });
    }
}