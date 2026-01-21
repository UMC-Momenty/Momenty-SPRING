package com.umc.momenty.global.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.umc.momenty.global.apiPayload.code.GeneralErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiProviderImpl implements GeminiProvider {

	private final Client geminiClient;

	@Value("${gemini.model}")
	private String model;

	@Override
	public String generateTextContent(String systemMessage, String userMessage) {
		GenerateContentResponse response;
		try {
			GenerateContentConfig config = GenerateContentConfig.builder()
				.systemInstruction(Content.fromParts(Part.fromText(systemMessage)))
				.build();

			response = geminiClient.models.generateContent(model, userMessage, config);
		} catch (ClientException e) {
			// API 키 오류, 할당량 부족 등 Google API 자체 오류
			log.warn("Gemini API 호출 중 오류 발생 [Message: {}]", e.getMessage());
			throw new GeneralException(GeneralErrorCode.GEMINI_ERROR);
		} catch (Exception e) {
			// 네트워크 타임아웃, 연결 실패 등 일반 예외
			log.warn("Gemini 연동 중 알 수 없는 오류 발생 [{}: {}]", e.getClass(), e.getMessage());
			throw new GeneralException(GeneralErrorCode.GEMINI_ERROR);
		}

		String result = response.text();
		if (!StringUtils.hasText(result)) {
			log.warn("Gemini로부터 빈 결과 반환");
			throw new GeneralException(GeneralErrorCode.GEMINI_ERROR);
		}

		return result;
	}
}
