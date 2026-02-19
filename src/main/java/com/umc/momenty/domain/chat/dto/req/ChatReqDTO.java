package com.umc.momenty.domain.chat.dto.req;

import java.util.List;

import com.umc.momenty.global.infra.gemini.enums.MimeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class ChatReqDTO {

    @Builder
    public record ChatRequest(
            String message,

            @Size(min = 1, max = 3, message = "파일은 1~3개만 업로드할 수 있습니다.")
            List<AttachmentDTO> files
    ){}

    @Builder
    public record AttachmentDTO(
        @NotBlank
            String fileKey,
            MimeType mimeType
    ){}

    public record AttachmentCreateDTO(

            @Size(min = 1, max = 3, message = "파일은 1~3개만 업로드할 수 있습니다.")
            List<MimeType> mimeTypes
    ){}
}
