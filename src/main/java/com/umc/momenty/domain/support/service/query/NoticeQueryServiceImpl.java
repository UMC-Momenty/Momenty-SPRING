package com.umc.momenty.domain.support.service.query;


import com.umc.momenty.domain.support.converter.NoticeConverter;
import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.domain.support.entity.Notice;
import com.umc.momenty.domain.support.exception.NoticeException;
import com.umc.momenty.domain.support.exception.code.NoticeErrorCode;
import com.umc.momenty.domain.support.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeResDTO.NoticeDTO getNotice(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOTICE_NOT_FOUND));

        return NoticeConverter.toNoticeDTO(notice);
    }
}
