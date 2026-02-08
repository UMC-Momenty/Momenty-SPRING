package com.umc.momenty.domain.chat.specialization.classifier;

import com.umc.momenty.domain.chat.enums.PetQuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetQuestionTypeResolver {

    private final PetQuestionClassifier keywordClassifier;
    private final PetQuestionAIClassifier aiClassifier;

    public PetQuestionType resolve(String message) {

        // 1차 : 키워드 기반
        PetQuestionType type = keywordClassifier.classify(message);

        // 2차 : 애매하면 AI
        if (type == PetQuestionType.UNKNOWN) {
            type = aiClassifier.classifyByAI(message);
        }

        // 3차
        return type != null ? type : PetQuestionType.GENERAL_INFO;
    }
}
