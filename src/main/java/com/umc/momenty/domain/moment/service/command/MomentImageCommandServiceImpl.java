package com.umc.momenty.domain.moment.service.command;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;
import com.umc.momenty.global.infra.s3.enums.ImageContentType;
import com.umc.momenty.global.infra.s3.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MomentImageCommandServiceImpl implements MomentImageCommandService{

    private final ImageUploadService imageUploadService;

    @Transactional
    @Override
    public List<PresignedUrlResponse> generate(MomentReqDTO.MomentImageCreateDTO request) {

        return imageUploadService.generatePresignedUrls(
                "moments",
                request.imageTypes().stream()
                        .map(ImageContentType::getMimeType)
                        .toList()
        );
    }
}
