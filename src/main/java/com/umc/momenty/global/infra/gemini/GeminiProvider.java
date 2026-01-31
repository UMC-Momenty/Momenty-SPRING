package com.umc.momenty.global.infra.gemini;

import java.util.Map;

import com.umc.momenty.global.infra.gemini.enums.MimeType;

public interface GeminiProvider {
	String generateTextContent(String systemMessage, String userMessage);

	// s3Urls :
	// <S3_Presigned_Url, MimeType>
	// ex) <"https://momenty....png", MimeType.Image.PNG>
	String generateTextContentWithFiles(String systemMessage, String userMessage, Map<String, MimeType.MimeTypeExtension> s3Urls);
}
