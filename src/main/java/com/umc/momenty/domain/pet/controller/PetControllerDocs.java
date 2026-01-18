package com.umc.momenty.domain.pet.controller;

import com.umc.momenty.domain.pet.dto.req.PetReqDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface PetControllerDocs {

    @Operation(
            summary = "반려동물 프로필 추가 API",
            description = "해당 사용자의 반려동물 프로필을 추가합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 또는 존재하지 않는 품종")
    })
    ApiResponse<Void> createPetProfile(
            @Parameter(description = "사용자 ID")
            Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "프로필 추가 내용 \n\n"
                            + "**요청 예시:**\n"
                            + "- profileImageUrl: \"https://...\""
                            + "- petName: \"example\"\n"
                            + "- gender: \"FEMALE\"\n"
                            + "- birth: \"2000-01-01\"\n"
                            + "- species: \"CAT or DOG\"\n"
                            + "- breedId: \"1\"\n"
                            + "- intro: \"우리 아기는 너무 귀엽고요 ...\"",
                    required = true
            )
            PetReqDTO.PetProfileDTO petProfileDTO
    );
}
