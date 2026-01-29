package com.umc.momenty.global.infra.gemini.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MimeType {

	public interface MimeTypeExtension {
		String getExtension();
	}

	@Getter
	@RequiredArgsConstructor
	public enum Application implements MimeTypeExtension {
		PDF("application/pdf");

		private final String extension;
	}

	@Getter
	@RequiredArgsConstructor
	public enum Audio implements MimeTypeExtension {
		WAV("audio/wav"),
		MP3("audio/mp3"),
		AIFF("audio/aiff"),
		AAC("audio/aac"),
		OGG("audio/ogg"),
		FLAC("audio/flac");

		private final String extension;
	}

	@Getter
	@RequiredArgsConstructor
	public enum Image implements MimeTypeExtension {
		PNG("image/png"),
		JPEG("image/jpeg"),
		WEBP("image/webp"),
		HEIC("image/heic"),
		HEIF("image/heif");

		private final String extension;
	}

	@Getter
	@RequiredArgsConstructor
	public enum Text implements MimeTypeExtension {
		PLAIN("text/plain");

		private final String extension;
	}

	@Getter
	@RequiredArgsConstructor
	public enum Video implements MimeTypeExtension {
		MP4("video/mp4"),
		MPEG("video/mpeg"),
		MOV("video/mov"),
		AVI("video/avi"),
		X_FLV("video/x-flv"),
		MPG("video/mpg"),
		WEBM("video/webm"),
		WMV("video/wmv"),
		GPP3("video/3gpp");

		private final String extension;
	}
}
