package com.umc.momenty.domain.moment.service.command;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;

public interface MomentCommandService {
    void createMoment(Long userId, Long petId, MomentReqDTO.MomentDTO request);
}
