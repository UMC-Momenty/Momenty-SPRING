package com.umc.momenty.domain.pet.controller;

import java.util.List;

import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import com.umc.momenty.domain.pet.exception.code.PetSuccessCode;
import com.umc.momenty.domain.pet.service.command.PetCommandService;
import com.umc.momenty.domain.pet.service.query.PetQueryService;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PetController implements PetControllerDocs {

    private final PetCommandService petCommandService;
    private final PetQueryService petQueryService;

    @PostMapping("/users/{userId}/pets")
    @Override
    public ApiResponse<Void> createPetProfile(
            @PathVariable Long userId,
            @Valid @RequestBody PetReqDTO.PetProfileDTO petProfileDTO
    ){
        petCommandService.createPetProfile(userId, petProfileDTO);
        return ApiResponse.onSuccess(PetSuccessCode.PET_CREATED, null);
    }

    @PatchMapping("/users/{userId}/pets/{petId}")
    @Override
    public ApiResponse<Void> updatePetProfile(
            @PathVariable Long userId,
            @PathVariable Long petId,
            @Valid @RequestBody PetReqDTO.PetUpdateDTO petUpdateDTO
    ) {
        petCommandService.updatePetProfile(userId, petId, petUpdateDTO);
        return ApiResponse.onSuccess(PetSuccessCode.PET_UPDATED, null);
    }

    @GetMapping("/pets/my")
    @Override
    public ApiResponse<List<PetResDTO.MyPetDTO>> getMyPetList(
        @AuthUser Long userId
    ) {
        return ApiResponse.onSuccess(PetSuccessCode.PET_FOUND, petQueryService.getMyPetList(userId));
    }
}
