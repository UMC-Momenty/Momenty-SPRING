package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.FaqResDTO;
import com.umc.momenty.domain.support.exception.code.FaqSuccessCode;
import com.umc.momenty.domain.support.service.query.FaqQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController implements FaqControllerDocs {

    private final FaqQueryService faqQueryService;

    @GetMapping("/{faqId}")
    @Override
    public ApiResponse<FaqResDTO.FaqDTO> getFaq(@PathVariable Long faqId){
        return ApiResponse.onSuccess(FaqSuccessCode.FAQ_DETAIL_FOUND, faqQueryService.getFaq(faqId));
    }

    @GetMapping
    @Override
    public ApiResponse<List<FaqResDTO.FaqListDTO>> getAllFaq(){
        return ApiResponse.onSuccess(FaqSuccessCode.FAQ_LIST_FOUND, faqQueryService.getAllFaq());
    }
}
