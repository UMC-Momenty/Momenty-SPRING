package com.umc.momenty.domain.support.controller;

import com.umc.momenty.domain.support.dto.res.AppResDTO;
import com.umc.momenty.domain.support.exception.code.AppSuccessCode;
import com.umc.momenty.domain.support.service.query.AppQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app")
@RequiredArgsConstructor
public class AppController implements AppControllerDocs{

    private final AppQueryService appQueryService;

    @GetMapping("/{appId}")
    @Override
    public ApiResponse<AppResDTO.AppDTO> getApp(@PathVariable Long appId){
        return ApiResponse.onSuccess(AppSuccessCode.APP_DETAIL_FOUND, appQueryService.getApp(appId));
    }

    @GetMapping
    @Override
    public ApiResponse<List<AppResDTO.AppListDTO>> getAllApp(){
        return ApiResponse.onSuccess(AppSuccessCode.APP_LIST_FOUND, appQueryService.getAllApp());
    }
}
