package com.umc.momenty.domain.support.service.command;

import com.umc.momenty.domain.support.dto.req.InquiryReqDTO;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;

import java.util.List;

public interface InquiryImageCommandService {

    List<PresignedUrlResponse> generate(InquiryReqDTO.InquiryImageCreateDTO contentTypes);
}
