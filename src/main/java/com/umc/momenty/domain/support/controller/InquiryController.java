package com.umc.momenty.domain.support.controller;


import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.exception.code.InquirySuccessCode;
import com.umc.momenty.domain.support.service.query.InquiryQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController implements InquiryControllerDocs{

    private final InquiryQueryService inquiryQueryService;

    @GetMapping("/{inquiryId}")
    @Override
    public ApiResponse<InquiryResDTO.InquiryDTO> getInquiry(
            @PathVariable Long inquiryId
    ){
        // TODO : JWT 도입 시 로그인한 사용자와 inquiry.user 비교해서 접근 권한 검증 필요
        return ApiResponse.onSuccess(InquirySuccessCode.INQUIRY_DETAIL_FOUND, inquiryQueryService.getInquiry(inquiryId));
    }
}
