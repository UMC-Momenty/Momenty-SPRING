package com.umc.momenty.domain.support.controller;


import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.exception.code.InquirySuccessCode;
import com.umc.momenty.domain.support.service.command.InquiryCommandService;
import com.umc.momenty.domain.support.service.query.InquiryQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController implements InquiryControllerDocs{

    private final InquiryQueryService inquiryQueryService;
    private final InquiryCommandService inquiryCommandService;

    @GetMapping("/{inquiryId}")
    @Override
    public ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(
            @PathVariable Long inquiryId
    ){
        // TODO : JWT 도입 시 로그인한 사용자와 inquiry.user 비교해서 접근 권한 검증 필요
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_DETAIL_FOUND, inquiryQueryService.getInquiry(inquiryId));
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
}
