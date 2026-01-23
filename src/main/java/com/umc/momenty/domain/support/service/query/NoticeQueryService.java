package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import org.springframework.data.domain.Pageable;

public interface NoticeQueryService {

    NoticeResDTO.NoticeDTO getNotice(Long noticeId);
    NoticeResDTO.NoticePageDTO getAllNotice(Pageable pageable);
}
