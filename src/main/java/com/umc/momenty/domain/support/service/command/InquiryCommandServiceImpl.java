package com.umc.momenty.domain.support.service.command;

import com.umc.momenty.domain.support.converter.InquiryConverter;
import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.support.repository.InquiryRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryCommandServiceImpl implements  InquiryCommandService {

    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public void createInquiry(Long userId, InquiryReqDTO.InquiryDTO inquiryDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Inquiry inquiry = InquiryConverter.toInquiry(user, inquiryDTO);

        inquiryRepository.save(inquiry);
    }
}
