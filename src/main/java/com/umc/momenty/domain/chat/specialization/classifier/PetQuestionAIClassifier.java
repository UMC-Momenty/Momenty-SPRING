package com.umc.momenty.domain.chat.specialization.classifier;

import com.umc.momenty.domain.chat.enums.PetQuestionType;
import com.umc.momenty.global.infra.gemini.GeminiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetQuestionAIClassifier {

    private final GeminiProvider geminiProvider;

    public PetQuestionType classifyByAI(String message) {

        String systemMessage = """
                너는 반려동물 질문 분류기야.
                반드시 다음 중 하나만 반환해.

                HEALTH_RELATED
                BEHAVIOR
                GENERAL_INFO
                UNKNOWN
                """;

        String userMessage = """
                질문:
                "%s"

                ENUM 값 하나만 출력해.
                """.formatted(message);

        String result = geminiProvider.generateTextContent(systemMessage, userMessage);

        return parse(result);
    }

    private PetQuestionType parse(String result) {
        try {
            return PetQuestionType.valueOf(result.trim());
        } catch (Exception e) {
            return PetQuestionType.UNKNOWN;
        }
    }
}
