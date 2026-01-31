package com.umc.momenty.global.dto.res;

import lombok.Builder;

public class PageResDTO {

    @Builder
    public record PageInfoDTO(
            int page,
            int size,
            int totalPages,
            long totalElements,
            boolean hasNext,
            boolean hasPrevious
    ) {}
}
