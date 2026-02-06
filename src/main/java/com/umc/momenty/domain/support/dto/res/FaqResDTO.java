package com.umc.momenty.domain.support.dto.res;

import com.umc.momenty.domain.support.enums.FaqCategory;
import lombok.Builder;

public class FaqResDTO {

    @Builder
    public record FaqDTO(
            Long faqId,
            FaqCategory category,
            String question,
            String answer
    ){}

    @Builder
    public record FaqListDTO(
            Long faqId,
            FaqCategory category,
            String question
    ){}
}
