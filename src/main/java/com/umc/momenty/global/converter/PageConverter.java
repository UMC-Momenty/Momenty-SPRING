package com.umc.momenty.global.converter;

import com.umc.momenty.global.dto.res.PageResDTO;
import org.springframework.data.domain.Page;

public class PageConverter {

    public static PageResDTO.PageInfoDTO toPageInfoDTO(Page<?> page) {
        return PageResDTO.PageInfoDTO.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
