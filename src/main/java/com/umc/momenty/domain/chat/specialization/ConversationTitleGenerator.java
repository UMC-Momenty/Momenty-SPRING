package com.umc.momenty.domain.chat.specialization;

import com.umc.momenty.global.infra.gemini.GeminiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversationTitleGenerator {

    private final GeminiProvider geminiProvider;

    public String generate(String firstMessage) {
        String prompt =
                "다음 대화를 10자 이내의 대화 제목으로 요약해줘.";

        return geminiProvider.generateTextContent(prompt, firstMessage);
    }
}
