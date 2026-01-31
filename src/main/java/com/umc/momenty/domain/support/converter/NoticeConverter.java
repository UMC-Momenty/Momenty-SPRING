package com.umc.momenty.domain.support.converter;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.domain.support.entity.Notice;
import com.umc.momenty.global.converter.PageConverter;
import org.springframework.data.domain.Page;

public class NoticeConverter {

    public static NoticeResDTO.NoticeDTO toNoticeDTO(Notice notice) {
        return NoticeResDTO.NoticeDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeResDTO.NoticeListDTO toNoticeListDTO(Notice notice) {
        return NoticeResDTO.NoticeListDTO.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeResDTO.NoticePageDTO toNoticePageDTO(Page<Notice> page) {
        return NoticeResDTO.NoticePageDTO.builder()
                .notices(
                        page.getContent().stream()
                                .map(NoticeConverter::toNoticeListDTO)
                                .toList()
                )
                .pageInfo(PageConverter.toPageInfoDTO(page))
                .build();
    }
}
