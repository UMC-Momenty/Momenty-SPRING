package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.converter.InquiryConverter;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.support.exception.InquiryException;
import com.umc.momenty.domain.support.exception.code.InquiryErrorCode;
import com.umc.momenty.domain.support.repository.InquiryRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryQueryServiceImpl implements  InquiryQueryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    @Override
    public InquiryResDTO.InquiryDTO getInquiry(Long userId, Long inquiryId){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        if(!inquiry.getUser().getId().equals(userId)){
            throw new InquiryException(InquiryErrorCode.INQUIRY_ACCESS_DENIED);
        }

        return InquiryConverter.toInquiryDTO(inquiry);
    }

    @Override
    public InquiryResDTO.InquiryPageDTO getAllInquiry(Long userId, Pageable pageable){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Page<Inquiry> page = inquiryRepository.findAllByUser(user, pageable);

        if(pageable.getPageNumber() >= page.getTotalPages() && page.getTotalPages() > 0){
            throw new InquiryException(InquiryErrorCode.PAGE_OUT_OF_RANGE);
        }

        return InquiryConverter.toInquiryPageDTO(page);
    }
}
