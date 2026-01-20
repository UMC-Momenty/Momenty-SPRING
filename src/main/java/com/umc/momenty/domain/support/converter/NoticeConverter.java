package com.umc.momenty.domain.support.converter;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.domain.support.entity.Notice;

public class NoticeConverter {

    public static NoticeResDTO.NoticeDTO toNoticeDTO(Notice notice) {
        return NoticeResDTO.NoticeDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
