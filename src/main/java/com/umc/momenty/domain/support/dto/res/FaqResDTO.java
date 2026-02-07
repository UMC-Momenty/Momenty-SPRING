package com.umc.momenty.domain.support.dto.res;

import lombok.Builder;

public class FaqResDTO {

    @Builder
    public record FaqDTO(
            Long faqId,
            String question,
            String answer
    ){}

    @Builder
    public record FaqListDTO(
            Long faqId,
            String question
    ){}
}
