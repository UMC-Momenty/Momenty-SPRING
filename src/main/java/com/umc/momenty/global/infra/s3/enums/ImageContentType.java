package com.umc.momenty.global.infra.s3.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ImageContentType {
    JPEG("image/jpeg", List.of("jpg", "jpeg")),
    PNG("image/png", List.of("png"));

    private final String mimeType;
    private final List<String> extensions;
}
