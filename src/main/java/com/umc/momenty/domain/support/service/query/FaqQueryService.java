package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.FaqResDTO;
import com.umc.momenty.domain.support.entity.FAQ;
import com.umc.momenty.domain.support.enums.FaqCategory;

import java.util.List;
import java.util.Optional;

public interface FaqQueryService {

    FaqResDTO.FaqDTO getFaq(Long faqId);
    List<FaqResDTO.FaqListDTO> getAllFaq();

    Optional<FAQ> findByCategory(FaqCategory category);
}
