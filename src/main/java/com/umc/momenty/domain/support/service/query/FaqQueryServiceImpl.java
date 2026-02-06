package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.converter.FaqConverter;
import com.umc.momenty.domain.support.dto.res.FaqResDTO;
import com.umc.momenty.domain.support.entity.FAQ;
import com.umc.momenty.domain.support.exception.FaqException;
import com.umc.momenty.domain.support.exception.code.FaqErrorCode;
import com.umc.momenty.domain.support.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqQueryServiceImpl implements FaqQueryService {

    private final FaqRepository faqRepository;

    @Override
    public FaqResDTO.FaqDTO getFaq(Long faqId){
        FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new FaqException(FaqErrorCode.FAQ_NOT_FOUND));

        return FaqConverter.toFaqDTO(faq);
    }

    @Override
    public List<FaqResDTO.FaqListDTO> getAllFaq() {
        return FaqConverter.toFaqListDTO(faqRepository.findAllByActiveTrue());
    }
}
