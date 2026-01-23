package com.umc.momenty.domain.support.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeResDTO {

    @Builder
    public record NoticeDTO(
            Long noticeId,
            String title,
            String content,
            LocalDateTime createdAt
    ){}

    @Builder
    public record NoticeListDTO(
            Long noticeId,
            String title,
            LocalDateTime createdAt
    ){}

    @Builder
    public record PageInfoDTO(
            int page,
            int size,
            int totalPages,
            long totalElements,
            boolean hasNext,
            boolean hasPrevious
    ) {}

    @Builder
    public record NoticePageDTO(
            List<NoticeListDTO> notices,
            PageInfoDTO pageInfo
    ) {}
}
