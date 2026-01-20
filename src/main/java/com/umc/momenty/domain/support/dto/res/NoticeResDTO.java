package com.umc.momenty.domain.support.dto.res;

import lombok.Builder;

public class NoticeResDTO {

    @Builder
    public record NoticeDTO(
            Long noticeId,
            String title,
            String content
    ){}
}
