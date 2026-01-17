package com.umc.momenty.global.infra;

public interface GeminiProvider {
	String generateTextContent(String systemMessage, String userMessage);
}
