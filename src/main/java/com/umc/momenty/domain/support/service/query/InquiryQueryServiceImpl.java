package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.converter.InquiryConverter;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.support.exception.InquiryException;
import com.umc.momenty.domain.support.exception.code.InquiryErrorCode;
import com.umc.momenty.domain.support.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryQueryServiceImpl implements  InquiryQueryService {

    private final InquiryRepository inquiryRepository;

    @Override
    public InquiryResDTO.InquiryDTO getInquiry(Long inquiryId){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        return InquiryConverter.toInquiryDTO(inquiry);
    }
}
