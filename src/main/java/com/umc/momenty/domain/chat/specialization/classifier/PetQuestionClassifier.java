package com.umc.momenty.domain.chat.specialization.classifier;

import com.umc.momenty.domain.chat.enums.PetQuestionType;
import org.springframework.stereotype.Component;

@Component
public class PetQuestionClassifier {

    private static final String[] HEALTH_KEYWORDS = {
            "아파", "피", "통증", "절뚝", "구토", "설사"
    };

    private static final String[] BEHAVIOR_KEYWORDS = {
            "행동", "짖", "물어", "숨", "공격", "불안"
    };

    private static final String[] GENERAL_KEYWORDS = {
            "먹어도", "괜찮아", "해도 돼", "가능"
    };

    private static final String[] SERVICE_KEYWORDS = {
            "회원가입", "로그인", "결제", "무료",
            "구독", "요금", "기록", "사진", "삭제",
            "여러 마리", "마리"
    };

    public PetQuestionType classify(String message) {

        int healthScore = score(message, HEALTH_KEYWORDS);
        int behaviorScore = score(message, BEHAVIOR_KEYWORDS);
        int serviceScore = score(message, SERVICE_KEYWORDS);
        int generalScore = score(message, GENERAL_KEYWORDS);

        if (healthScore > 0) return PetQuestionType.HEALTH_RELATED;
        if (behaviorScore > 0) return PetQuestionType.BEHAVIOR;
        if (serviceScore > 0) return PetQuestionType.SERVICE_INFO;
        if (generalScore > 0) return PetQuestionType.GENERAL_INFO;

        return PetQuestionType.UNKNOWN;
    }

    private int score(String message, String[] keywords) {
        int score = 0;
        for (String keyword : keywords) {
            if (message.contains(keyword)) score++;
        }
        return score;
    }
}
