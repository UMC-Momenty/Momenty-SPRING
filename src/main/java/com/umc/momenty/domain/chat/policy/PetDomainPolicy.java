package com.umc.momenty.domain.chat.policy;

import org.springframework.stereotype.Component;

@Component
public class PetDomainPolicy {

    public PolicyResult validate(String message) {

        if (isExplicitDiagnosisRequest(message)
                || isExplicitPrescriptionRequest(message)) {

            return PolicyResult.reject(
                    "정확한 진단이나 치료·처방 판단은 수의사 상담이 필요해요 🐾"
            );
        }

        return PolicyResult.allow();
    }

    private boolean isExplicitDiagnosisRequest(String message) {
        return message.contains("진단해")
                || message.contains("병명")
                || message.contains("무슨 병")
                || message.contains("확정해줘");
    }

    private boolean isExplicitPrescriptionRequest(String message) {
        return message.contains("약 처방")
                || message.contains("이 약 먹여")
                || message.contains("약 줘")
                || message.contains("수술해도 돼");
    }
}
