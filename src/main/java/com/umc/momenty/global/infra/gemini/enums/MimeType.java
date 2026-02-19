package com.umc.momenty.global.infra.gemini.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MimeType {
	// audio
	WAV("audio/wav"),
	MP3("audio/mp3"),
	AIFF("audio/aiff"),
	AAC("audio/aac"),
	OGG("audio/ogg"),
	FLAC("audio/flac"),

	// application
	PDF("application/pdf"),

	// image
	PNG("image/png"),
	JPEG("image/jpeg"),
	JPG("image/jpg"),
	WEBP("image/webp"),
	HEIC("image/heic"),
	HEIF("image/heif"),

	// text
	PLAIN("text/plain"),

	// video
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
