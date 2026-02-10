package com.umc.momenty.domain.chat.dto.req;

import lombok.Builder;

public class ChatReqDTO {

    @Builder
    public record ChatRequest(
            String message
    ){}
}
