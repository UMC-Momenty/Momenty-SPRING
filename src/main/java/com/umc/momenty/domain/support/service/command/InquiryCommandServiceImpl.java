package com.umc.momenty.domain.support.service.command;

import com.umc.momenty.domain.support.converter.InquiryConverter;
import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.support.repository.InquiryRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.global.infra.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryCommandServiceImpl implements  InquiryCommandService {

    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;
    private final S3Service s3Service;

    @Override
    public void createInquiry(Long userId, InquiryReqDTO.InquiryDTO inquiryDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<String> imageUrls = inquiryDTO.images() == null
                ? List.of()
                : inquiryDTO.images().stream()
                .map(image -> s3Service.buildImageUrl(image.imageKey()))
                .toList();

        Inquiry inquiry = InquiryConverter.toInquiry(user, inquiryDTO, imageUrls);

        inquiryRepository.save(inquiry);
    }
}
