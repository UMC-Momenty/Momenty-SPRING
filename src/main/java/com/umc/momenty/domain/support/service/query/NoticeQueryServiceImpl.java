package com.umc.momenty.domain.support.service.query;


import com.umc.momenty.domain.support.converter.NoticeConverter;
import com.umc.momenty.domain.support.dto.res.NoticeResDTO;
import com.umc.momenty.domain.support.entity.Notice;
import com.umc.momenty.domain.support.exception.NoticeException;
import com.umc.momenty.domain.support.exception.code.NoticeErrorCode;
import com.umc.momenty.domain.support.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeResDTO.NoticeDTO getNotice(Long noticeId){
        Notice notice = noticeRepository.findByIdAndActiveTrue(noticeId)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOTICE_NOT_FOUND));

        return NoticeConverter.toNoticeDTO(notice);
    }

    @Override
    public NoticeResDTO.NoticePageDTO getAllNotice(Pageable pageable){
        Page<Notice> page = noticeRepository.findAllByActiveTrue(pageable);

        if(pageable.getPageNumber() >= page.getTotalPages() && page.getTotalPages() > 0){
            throw new NoticeException(NoticeErrorCode.PAGE_OUT_OF_RANGE);
        }

        return NoticeConverter.toNoticePageDTO(page);
    }
}
