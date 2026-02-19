package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.AppResDTO;

import java.util.List;

public interface AppQueryService {
    AppResDTO.AppDTO getApp(Long appId);
    List<AppResDTO.AppListDTO> getAllApp();
}
