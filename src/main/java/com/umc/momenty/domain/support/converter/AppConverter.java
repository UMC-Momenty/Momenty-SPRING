package com.umc.momenty.domain.support.converter;

import com.umc.momenty.domain.support.dto.res.AppResDTO;
import com.umc.momenty.domain.support.entity.App;

import java.util.List;

public class AppConverter {

    public static AppResDTO.AppDTO toAppDTO(App app){
        return AppResDTO.AppDTO.builder()
                .appId(app.getId())
                .title(app.getTitle())
                .content(app.getContent())
                .build();
    }

    public static AppResDTO.AppListDTO toAppList(App app){
        return AppResDTO.AppListDTO.builder()
                .appId(app.getId())
                .title(app.getTitle())
                .build();
    }

    public static List<AppResDTO.AppListDTO> toAppListDTO(List<App> apps){
        return apps.stream()
                .map(AppConverter::toAppList)
                .toList();
    }
}
