package com.umc.momenty.domain.chat.specialization.faq;

import com.umc.momenty.domain.support.enums.FaqCategory;
import org.springframework.stereotype.Component;

@Component
public class FaqCategoryResolver {

    public FaqCategory resolve(String message) {

        if (message.contains("회원가입") || message.contains("로그인")) {
            return FaqCategory.ACCOUNT;
        }

        if (message.contains("여러 마리") || message.contains("마리")) {
            return FaqCategory.PET;
        }

        if (message.contains("기록") || message.contains("사진")) {
            return FaqCategory.MOMENT;
        }

        if (message.contains("의료") || message.contains("상담")) {
            return FaqCategory.CHATBOT;
        }

        if (message.contains("무료") || message.contains("결제")) {
            return FaqCategory.POLICY;
        }

        return FaqCategory.ETC;
    }
}
