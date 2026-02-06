package com.umc.momenty.domain.support.controller;


import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.exception.code.InquirySuccessCode;
import com.umc.momenty.domain.support.service.command.InquiryCommandService;
import com.umc.momenty.domain.support.service.command.InquiryImageCommandService;
import com.umc.momenty.domain.support.service.query.InquiryQueryService;
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
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController implements InquiryControllerDocs{

    private final InquiryQueryService inquiryQueryService;
    private final InquiryCommandService inquiryCommandService;
    private final InquiryImageCommandService inquiryImageCommandService;

    @GetMapping("/{inquiryId}")
    @Override
    public ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(
            @PathVariable Long inquiryId
    ){
        // TODO : JWT 도입 시 로그인한 사용자와 inquiry.user 비교해서 접근 권한 검증 필요
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_DETAIL_FOUND, inquiryQueryService.getInquiry(inquiryId));
    }

    @GetMapping("/user/{userId}")
    @Override
    public ApiResponse<InquiryResDTO.InquiryPageDTO> getAllInquiry(
            @PathVariable Long userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_LIST_FOUND, inquiryQueryService.getAllInquiry(userId, pageable));
    }

    @PostMapping("/{userId}")
    @Override
    public ApiResponse<Void> createInquiry(
            @PathVariable Long userId,
            @Valid @RequestBody InquiryReqDTO.InquiryDTO inquiryDTO
    ){
        inquiryCommandService.createInquiry(userId, inquiryDTO);
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_CREATED, null);
    }

    @PostMapping("/image")
    @Override
    public ApiResponse<List<PresignedUrlResponse>> createInquiryImage(@Valid @RequestBody InquiryReqDTO.InquiryImageCreateDTO request){
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_IMAGE_CREATED, inquiryImageCommandService.generate(request));
    }
}
