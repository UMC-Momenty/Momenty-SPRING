package com.umc.momenty.global.infra.s3.dto.response;

import lombok.Builder;

@Builder
public record PresignedUrlResponse(
        String key,
        String url
) {}