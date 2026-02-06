package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.dto.res.FaqResDTO;

import java.util.List;

public interface FaqQueryService {

    FaqResDTO.FaqDTO getFaq(Long faqId);
    List<FaqResDTO.FaqListDTO> getAllFaq();
}
