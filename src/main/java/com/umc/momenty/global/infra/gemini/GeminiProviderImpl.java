package com.umc.momenty.global.infra.gemini;

import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.umc.momenty.global.apiPayload.code.GeneralErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.infra.gemini.enums.MimeType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiProviderImpl implements GeminiProvider {

	private final Client geminiClient;

	@Value("${gemini.model}")
	private String model;

	@Override
	public String generateTextContent(String systemMessage, String userMessage) {
		GenerateContentConfig.Builder configBuilder = GenerateContentConfig.builder();
		if (StringUtils.hasText(systemMessage)) {
			configBuilder.systemInstruction(Content.fromParts(Part.fromText(systemMessage)));
		}
		
		Content userContent = Content.fromParts(Part.fromText(userMessage));
		return generateContent(configBuilder.build(), userContent);
	}

	@Override
	public String generateTextContentWithFiles(String systemMessage, String userMessage, Map<String, MimeType> s3Urls) {
		GenerateContentConfig.Builder configBuilder = GenerateContentConfig.builder();
		if (StringUtils.hasText(systemMessage)) {
			configBuilder.systemInstruction(Content.fromParts(Part.fromText(systemMessage)));
		}

		List<Part> userParts = new ArrayList<>();
		userParts.add(Part.fromText(userMessage));
		s3Urls.entrySet().stream()
			.map(e -> Part.fromUri(e.getKey(), e.getValue().getExtension()))
			.forEach(userParts::add);

		Content userContent = Content.fromParts(userParts.toArray(new Part[0]));

		return generateContent(configBuilder.build(), userContent);
	}

	private String generateContent(GenerateContentConfig config, Content content) {
		GenerateContentResponse response;
		try {
			response = geminiClient.models.generateContent(model, content, config);
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
