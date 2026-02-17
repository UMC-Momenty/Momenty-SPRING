package com.umc.momenty.domain.chat.specialization.context;

import com.umc.momenty.domain.chat.enums.PetQuestionType;
import com.umc.momenty.domain.chat.specialization.faq.FaqCategoryResolver;
import com.umc.momenty.domain.support.entity.FAQ;
import com.umc.momenty.domain.support.enums.FaqCategory;
import com.umc.momenty.domain.support.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PetChatContextProvider {

    private final FaqRepository faqRepository;
    private final FaqCategoryResolver faqCategoryResolver;

    public String getRelevantContext(PetQuestionType type, String message) {

        if (type != PetQuestionType.SERVICE_INFO) return "";

        FaqCategory category = faqCategoryResolver.resolve(message);

        List<FAQ> faqs = faqRepository.findByCategoryAndActiveTrue(category);

        if (faqs.isEmpty()) {
            return "";
        }

        return faqs.stream()
                .map(faq -> """
            Q. %s
            A. %s
            """.formatted(faq.getQuestion(), faq.getAnswer()))
                .collect(Collectors.joining("\n\n"));
    }
}