package com.umc.momenty.domain.chat.dto.res;

import com.umc.momenty.domain.chat.enums.Role;
import com.umc.momenty.domain.chat.enums.PetQuestionType;
import lombok.Builder;

public class ChatResDTO {

    @Builder
    public record ChatResponse(
            Long chatId,
            Role role,
            String answer,

            PetQuestionType questionType
    ){}
}
