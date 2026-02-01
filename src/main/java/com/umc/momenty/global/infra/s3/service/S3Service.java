package com.umc.momenty.global.infra.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 업로드 Presigned URL 생성
    public PresignedUrlResponse generatePresignedUploadUrl(String key) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 10); // 10분 후 만료

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.PUT) // PUT 요청 (업로드)
                        .withExpiration(expiration);

        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return new PresignedUrlResponse(key, presignedUrl.toString());
    }

    // 다운로드 Presigned URL 생성
    public PresignedUrlResponse generatePresignedDownloadUrl(String key) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 10); // 10분 후 만료

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET) // GET 요청 (다운로드)
                        .withExpiration(expiration);

        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return new PresignedUrlResponse(key, presignedUrl.toString());
    }
}