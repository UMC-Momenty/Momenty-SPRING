package com.umc.momenty.global.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GeminiProviderImpl implements GeminiProvider {

	private final Client geminiClient;

	@Value("${gemini.model}")
	private String model;

	@Override
	public String generateTextContent(String systemMessage, String userMessage) {
		GenerateContentConfig config =
			GenerateContentConfig.builder()
				.systemInstruction(
					Content.fromParts(Part.fromText(systemMessage)))
				.build();

		GenerateContentResponse response =
			geminiClient.models.generateContent(
				model,
				userMessage,
				config);

		return response.text();
	}
}
