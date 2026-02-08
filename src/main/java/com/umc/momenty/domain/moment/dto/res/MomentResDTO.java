package com.umc.momenty.domain.moment.dto.res;

import com.umc.momenty.domain.moment.enums.Emotion;
import com.umc.momenty.global.dto.res.PageResDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class MomentResDTO {

    @Builder
    public record MomentDTO(
            Long momentId,
            Emotion emotion,
            List<MomentImageDTO> images,
            LocalDateTime createdAt,
            String content
    ){}

    @Builder
    public record MomentImageDTO(
            Long momentImageId,
            String imageUrl
    ){}

    @Builder
    public record MomentListDTO(
            Long momentId,
            Emotion emotion,
            LocalDateTime createdAt,
            String content
    ) {}

    @Builder
    public record MomentPageDTO(
            List<MomentListDTO> moments,
            PageResDTO.PageInfoDTO pageInfo
    ) {}

}
