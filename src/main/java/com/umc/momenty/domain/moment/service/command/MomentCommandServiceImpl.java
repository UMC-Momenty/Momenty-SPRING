package com.umc.momenty.domain.moment.service.command;

import com.umc.momenty.domain.moment.converter.MomentConverter;
import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.entity.Moment;
import com.umc.momenty.domain.moment.repository.MomentRepository;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.validator.PetValidator;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.global.infra.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MomentCommandServiceImpl implements MomentCommandService{

    private final S3Service s3Service;
    private final MomentRepository momentRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetValidator petValidator;


    @Override
    @Transactional
    public void createMoment(Long userId, Long petId, MomentReqDTO.MomentDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        petValidator.validatePetOwner(pet, user);

        List<String> imageUrls = request.images() == null
                ? List.of()
                : request.images().stream()
                .map(image -> s3Service.buildImageUrl(image.imageKey()))
                .toList();

        Moment moment = MomentConverter.toMoment(user, pet, request, imageUrls);
        momentRepository.save(moment);

    }

}
