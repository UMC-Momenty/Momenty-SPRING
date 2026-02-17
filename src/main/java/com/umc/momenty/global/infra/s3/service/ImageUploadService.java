package com.umc.momenty.global.infra.s3.service;

import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final S3Service s3Service;

    public List<PresignedUrlResponse> generatePresignedUrls(String prefix, List<String> mimeTypes) {
        return mimeTypes.stream()
                .map(type -> s3Service.generatePresignedUploadUrl(prefix, type))
                .toList();
    }
}
