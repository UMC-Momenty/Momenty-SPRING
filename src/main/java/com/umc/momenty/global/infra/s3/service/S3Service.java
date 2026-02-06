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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 업로드 Presigned URL 생성
    public PresignedUrlResponse generatePresignedUploadUrl(String prefix, String contentType) {
        String fileName = UUID.randomUUID().toString();
        String key = String.format("%s/%s", prefix, fileName);

        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getExpirationTime(10));

        request.setContentType(contentType);

        URL presignedUrl = amazonS3.generatePresignedUrl(request);
        return new PresignedUrlResponse(key, presignedUrl.toString());
    }

    // 다운로드 Presigned URL 생성
    public PresignedUrlResponse generatePresignedDownloadUrl(String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getExpirationTime(10));

        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return new PresignedUrlResponse(key, presignedUrl.toString());
    }

    public String buildImageUrl(String key) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
    }

    private Date getExpirationTime(int minutes) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + (1000L * 60 * minutes);
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}