package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import org.springframework.data.domain.Pageable;

public interface InquiryQueryService {

    InquiryResDTO.InquiryDTO getInquiry(Long inquiryId);
    InquiryResDTO.InquiryPageDTO getAllInquiry(Long userId, Pageable pageable);
}
