package com.umc.momenty.domain.moment.controller;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.dto.res.MomentResDTO;
import com.umc.momenty.domain.moment.exception.code.MomentSuccessCode;
import com.umc.momenty.domain.moment.service.command.MomentCommandService;
import com.umc.momenty.domain.moment.service.command.MomentImageCommandService;
import com.umc.momenty.domain.moment.service.query.MomentQueryService;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/moments")
public class MomentController implements MomentControllerDocs{

    private final MomentCommandService momentCommandService;
    private final MomentQueryService momentQueryService;
    private final MomentImageCommandService momentImageCommandService;

    @Override
    @PostMapping("pets/{petId}")
    public ApiResponse<MomentResDTO.MomentDTO> createMoment(
            @AuthUser Long userId,
            @PathVariable Long petId,
            @Valid @RequestBody MomentReqDTO.MomentDTO dto
    ){
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_CREATED,
                momentCommandService.createMoment(userId, petId, dto)
        );
    }

    @Override
    @PostMapping("/image")
    public ApiResponse<List<PresignedUrlResponse>> createMomentImage(
            @Valid @RequestBody MomentReqDTO.MomentImageCreateDTO request) {
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_IMAGE_CREATED,
                momentImageCommandService.generate(request)
        );
    }

    @Override
    @GetMapping("/pets/{petId}")
    public ApiResponse<MomentResDTO.MomentPageDTO> getMoments(
            @AuthUser Long userId,
            @PathVariable Long petId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_LIST_FOUND,
                momentQueryService.getMoments(userId, petId, pageable)
        );
    }

    @Override
    @GetMapping("/pets/{petId}/{momentId}")
    public ApiResponse<MomentResDTO.MomentDTO> getMoment(
            @AuthUser Long userId,
            @PathVariable Long petId,
            @PathVariable Long momentId
    ){
        return ApiResponse.onSuccess(
                MomentSuccessCode.MOMENT_DETAIL_FOUND,
                momentQueryService.getMoment(userId, petId, momentId)
        );
    }
}

