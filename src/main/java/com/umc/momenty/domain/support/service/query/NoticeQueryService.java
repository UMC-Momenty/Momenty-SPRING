package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.NoticeResDTO;

public interface NoticeQueryService {

    NoticeResDTO.NoticeDTO getNotice(Long noticeId);
}
