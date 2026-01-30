package com.umc.momenty.domain.support.dto.res;

import com.umc.momenty.domain.support.enums.InquiryCategory;
import com.umc.momenty.global.dto.res.PageResDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryResDTO {

    @Builder
    public record InquiryDTO(
            Long inquiryId,
            InquiryCategory type,
            String content,
            List<InquiryImageDTO> images,
            boolean isAnswered,
            String answer,
            LocalDateTime createdAt
    ){}

    @Builder
    public record InquiryImageDTO(
            Long inquiryImageId,
            String imageUrl
    ){}

    @Builder
    public record InquiryListDTO(
            Long inquiryId,
            InquiryCategory type,
            boolean isAnswered,
            LocalDateTime createdAt
    ){}

    @Builder
    public record InquiryPageDTO(
            List<InquiryListDTO> inquiries,
            PageResDTO.PageInfoDTO pageInfo
    ){}
}
