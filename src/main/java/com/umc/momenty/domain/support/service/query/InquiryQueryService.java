package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.InquiryResDTO;

public interface InquiryQueryService {

    InquiryResDTO.InquiryDTO getInquiry(Long inquiryId);
}
