package com.umc.momenty.domain.support.service.command;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.domain.support.exception.InquiryException;
import com.umc.momenty.domain.support.exception.code.InquiryErrorCode;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import com.umc.momenty.global.infra.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryImageCommandServiceImpl implements InquiryImageCommandService {

    private final S3Service s3Service;

    @Override
    public List<PresignedUrlResponse> generate(InquiryReqDTO.InquiryImageCreateDTO request) {

        if (request.imageTypes().size() > 2) {
            throw new InquiryException(InquiryErrorCode.INVALID_IMAGE_COUNT);
        }

        return request.imageTypes().stream()
                .map(type ->
                        s3Service.generatePresignedUploadUrl(
                                "inquiry",
                                type.getMimeType()
                        )
                )
                .toList();
    }
}
