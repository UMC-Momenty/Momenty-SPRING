package com.umc.momenty.domain.pet.validator;

import com.umc.momenty.domain.pet.entity.Breed;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.enums.Species;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.BreedRepository;
import com.umc.momenty.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetValidator {

    private final BreedRepository breedRepository;

    public Breed validateBreed(Long breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow(() -> new PetException(PetErrorCode.BREED_NOT_FOUND));
    }

    public void validateBreedSpecies(Breed breed, Species species) {
        if (!breed.getSpecies().equals(species)) {
            throw new PetException(PetErrorCode.INVALID_BREED_SPECIES);
        }
    }

    public void validatePetOwner(Pet pet, User user) {
        if (!pet.getUser().getId().equals(user.getId())) {
            throw new PetException(PetErrorCode.PET_ACCESS_DENIED);
        }
    }
}
