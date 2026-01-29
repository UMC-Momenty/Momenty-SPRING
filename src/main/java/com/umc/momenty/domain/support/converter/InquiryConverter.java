package com.umc.momenty.domain.support.converter;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.dto.res.InquiryResDTO;
import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.support.entity.InquiryImage;
import com.umc.momenty.domain.user.entity.User;

public class InquiryConverter {

    public static InquiryResDTO.InquiryDTO toInquiryDTO(Inquiry inquiry) {
        return InquiryResDTO.InquiryDTO.builder()
                .inquiryId(inquiry.getId())
                .type(inquiry.getType())
                .content(inquiry.getContent())
                .images(
                        inquiry.getImages().stream()
                                .map(InquiryConverter::toInquiryImageDTO)
                                .toList()
                )
                .isAnswered(inquiry.isAnswered())
                .answer(inquiry.getAnswer())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

    public static InquiryResDTO.InquiryImageDTO toInquiryImageDTO(InquiryImage image) {
        return InquiryResDTO.InquiryImageDTO.builder()
                .inquiryImageId(image.getId())
                .imageUrl(image.getImageUrl())
                .build();
    }

    public static Inquiry toInquiry(User user, InquiryReqDTO.InquiryDTO inquiryDTO){
        Inquiry inquiry = Inquiry.builder()
                .type(inquiryDTO.type())
                .content(inquiryDTO.content())
                .user(user)
                .build();

        if(inquiryDTO.images() != null){
            inquiryDTO.images().forEach(imageDTO ->
                    inquiry.addImage(
                            InquiryImage.builder()
                                    .imageUrl(imageDTO.imageUrl())
                                    .build()
                    )
            );
        }

        return inquiry;
    }
}
