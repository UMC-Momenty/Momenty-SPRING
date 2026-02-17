package com.umc.momenty.domain.support.dto.req;

import com.umc.momenty.global.infra.s3.enums.ImageContentType;
import com.umc.momenty.domain.support.enums.InquiryCategory;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

public class InquiryReqDTO {

    @Builder
    public record InquiryDTO(
            @NotNull
            InquiryCategory type,

            @NotBlank
            @Size(max = 500)
            String content,

            @Size(max = 2, message = "이미지는 최대 2장까지 업로드할 수 있습니다.")
            List<InquiryImageDTO> images
    ){}

    @Builder
    public record InquiryImageDTO(
            @NotBlank
            String imageKey
    ){}

    public record InquiryImageCreateDTO(

            @Size(min = 1, max = 2, message = "이미지는 1~2장만 업로드할 수 있습니다.")
            List<ImageContentType> imageTypes
    ){}
}
