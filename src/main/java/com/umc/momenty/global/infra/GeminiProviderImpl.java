package com.umc.momenty.global.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

@Component
public class GeminiProviderImpl implements GeminiProvider {

	@Value("${gemini.model}")
	private String model;

	@Override
	public String generateTextContent(String systemMessage, String userMessage) {
		Client client = new Client();

		GenerateContentConfig config =
			GenerateContentConfig.builder()
				.systemInstruction(
					Content.fromParts(Part.fromText(systemMessage)))
				.build();

		GenerateContentResponse response =
			client.models.generateContent(
				model,
				userMessage,
				config);

		client.close();
		return response.text();
	}
}
