package com.umc.momenty.domain.support.converter;

import com.umc.momenty.domain.support.dto.res.FaqResDTO;
import com.umc.momenty.domain.support.entity.FAQ;

import java.util.List;

public class FaqConverter {

    public static FaqResDTO.FaqDTO toFaqDTO(FAQ faq){
        return FaqResDTO.FaqDTO.builder()
                .faqId(faq.getId())
                .category(faq.getCategory())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }

    public static FaqResDTO.FaqListDTO toFaqList(FAQ faq){
        return FaqResDTO.FaqListDTO.builder()
                .faqId(faq.getId())
                .category(faq.getCategory())
                .question(faq.getQuestion())
                .build();
    }

    public static List<FaqResDTO.FaqListDTO> toFaqListDTO(List<FAQ> faqs){
        return faqs.stream()
                .map(FaqConverter::toFaqList)
                .toList();
    }
}
