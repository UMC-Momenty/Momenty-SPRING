package com.umc.momenty.domain.support.dto.res;

import com.umc.momenty.global.dto.res.PageResDTO;
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
    public record NoticePageDTO(
            List<NoticeListDTO> notices,
            PageResDTO.PageInfoDTO pageInfo
    ) {}
}
