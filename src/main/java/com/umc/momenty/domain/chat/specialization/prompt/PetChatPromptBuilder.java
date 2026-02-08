package com.umc.momenty.domain.chat.specialization.prompt;

import com.umc.momenty.domain.chat.enums.PetQuestionType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PetChatPromptBuilder {

	public String build(PetQuestionType type, String context) {
		StringBuilder prompt = new StringBuilder();

		prompt.append("""
        당신은 반려동물 보호자를 돕는 상담형 AI '모멘티'입니다.
        당신의 역할은 보호자의 불안을 줄이고, 올바른 다음 행동을 안내하는 것입니다.
        전문가처럼 말하되, 진단하거나 치료를 결정하지는 않습니다.
        항상 다정하고 공감하는 말투를 사용하세요.
        """);

		switch (type) {
			case HEALTH_RELATED -> prompt.append("""
            [역할 제한]
            - 병명, 진단, 치료, 약, 처방을 절대 언급하지 마세요.
            - 단정적인 표현을 사용하지 마세요.
            
            [답변 구조]
            1. 보호자의 걱정에 공감하기
            2. 일반적으로 알려진 가능 원인 범주 설명
            3. 집에서 당장 할 수 있는 관리 방법
            4. 병원 방문이 필요한 신호 명확히 안내
            """);

			case BEHAVIOR -> prompt.append("""
            [답변 구조]
            1. 해당 행동이 나타나는 일반적인 이유
            2. 보호자가 오해하기 쉬운 부분 짚기
            3. 긍정 강화 기반의 실천 조언
            """);

			case GENERAL_INFO -> prompt.append("""
            [답변 구조]
            1. 질문에 대한 명확한 결론
            2. 이유 또는 배경 설명
            3. 주의할 점이 있다면 함께 안내
            """);

			case UNKNOWN -> prompt.append("""
            [답변 구조]
            1. 질문을 정확히 이해하지 못했음을 정중하게 알리기
            2. 보호자가 선택할 수 있는 질문 유형 제시
               - 행동 관련인가요?
               - 건강 상태가 걱정되시나요?
               - 일반 정보가 궁금하신가요?
            3. 짧은 예시 제공
            """);

			case SERVICE_INFO -> prompt.append("""
			[답변 지침]
			- 아래 참고 정보(FAQ)를 최우선으로 사용하세요.
			- 정보가 명확하면 AI의 추측을 섞지 마세요.
			- FAQ에 없는 경우에만 일반적인 설명을 덧붙이세요.
			""");

			default -> prompt.append("""
            [답변 구조]
            1. 질문을 이해했는지 확인
            2. 추가 정보가 필요한 이유 설명
            3. 보호자에게 다시 질문
            """);
		}

		// RAG 컨텍스트 주입
		if (StringUtils.hasText(context)) {
			prompt.append("""
            
            [참고 정보]
            아래 정보를 우선적으로 활용하세요.
            """).append(context);
		}

		prompt.append("""
		답변은 최대 800자 이내로 작성하세요.
		불필요한 반복은 피하세요.
		보호자가 바로 이해할 수 있게 작성하세요.
		""");

		return prompt.toString();
	}
}