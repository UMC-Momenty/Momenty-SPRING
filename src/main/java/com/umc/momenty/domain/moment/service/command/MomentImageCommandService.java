package com.umc.momenty.domain.moment.service.command;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.global.infra.s3.dto.response.PresignedUrlResponse;

import java.util.List;

public interface MomentImageCommandService {
    List<PresignedUrlResponse> generate(MomentReqDTO.MomentImageCreateDTO contentTypes);
}
