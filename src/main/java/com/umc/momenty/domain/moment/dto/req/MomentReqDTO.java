package com.umc.momenty.domain.moment.dto.req;

import com.umc.momenty.domain.moment.enums.Emotion;
import com.umc.momenty.global.infra.s3.enums.ImageContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public class MomentReqDTO {

    @Builder
    public record MomentDTO(
            @NotEmpty(message = "최소 하나 이상의 사진이 필요합니다.")
            List<MomentImageDTO> images,

            @NotNull(message = "감정은 필수입니다.")
            Emotion emotion,

            @NotNull(message = "내용은 필수입니다.")
            String content

    ){}

    @Builder
    public record MomentImageDTO(
            @NotBlank
            String imageKey
    ){}

    public record MomentImageCreateDTO(
            @Size(min = 1, max = 10, message = "이미지는 1~10장만 업로드할 수 있습니다.")
            List<ImageContentType> imageTypes
    ){}
}
