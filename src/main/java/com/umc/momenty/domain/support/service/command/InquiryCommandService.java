package com.umc.momenty.domain.support.service.command;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;

public interface InquiryCommandService {

    void createInquiry(Long userId, InquiryReqDTO.InquiryDTO inquiryDTO);
}
