package com.umc.momenty.domain.moment.controller;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.exception.code.MomentSuccessCode;
import com.umc.momenty.domain.moment.service.command.MomentCommandService;
import com.umc.momenty.domain.moment.service.command.MomentImageCommandService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/moments")
public class MomentController{

    private final MomentCommandService momentCommandService;
    private final MomentImageCommandService momentImageCommandService;

    @PostMapping("users/{userId}/pets/{petId}")
    public ApiResponse<Void> createMoment(
            @PathVariable Long userId,
            @PathVariable Long petId,
            @Valid @RequestBody MomentReqDTO.MomentDTO dto
    ){
        momentCommandService.createMoment(userId, petId, dto);
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_CREATED,
                null);
    }

    @PostMapping("/image")
    public ApiResponse<List<PresignedUrlResponse>> createMomentImage(
            @Valid @RequestBody MomentReqDTO.MomentImageCreateDTO request) {
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_IMAGE_CREATED,
                momentImageCommandService.generate(request)
        );
    }
}
