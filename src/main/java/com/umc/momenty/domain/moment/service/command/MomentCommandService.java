package com.umc.momenty.domain.moment.service.command;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.dto.res.MomentResDTO;

public interface MomentCommandService {
    MomentResDTO.MomentDTO createMoment(Long userId, Long petId, MomentReqDTO.MomentDTO request);
}
