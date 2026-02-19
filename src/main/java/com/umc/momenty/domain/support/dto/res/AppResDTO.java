package com.umc.momenty.domain.support.dto.res;

import lombok.Builder;

public class AppResDTO {

    @Builder
    public record AppListDTO(
            Long appId,
            String title
    ){}

    @Builder
    public record AppDTO(
            Long appId,
            String title,
            String content
    ){}
}
