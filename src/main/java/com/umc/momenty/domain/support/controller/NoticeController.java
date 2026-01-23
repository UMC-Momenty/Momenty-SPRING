package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.domain.support.exception.code.NoticeSuccessCode;
import com.umc.momenty.domain.support.service.query.NoticeQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs{

    private final NoticeQueryService noticeQueryService;

    @GetMapping("/{noticeId}")
    @Override
    public ApiResponse<NoticeResDTO.NoticeDTO> getNotice(
            @PathVariable Long noticeId
    ){
        return ApiResponse.onSuccess(NoticeSuccessCode.NOTICE_DETAIL_FOUND,noticeQueryService.getNotice(noticeId));
    }

    @GetMapping("")
    public ApiResponse<NoticeResDTO.NoticePageDTO> getAllNotice(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ApiResponse.onSuccess(NoticeSuccessCode.NOTICE_LIST_FOUND, noticeQueryService.getAllNotice(pageable));
    }
}
