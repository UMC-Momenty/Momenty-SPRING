package com.umc.momenty.domain.moment.service.query;

import com.umc.momenty.domain.moment.dto.res.MomentResDTO;
import org.springframework.data.domain.Pageable;

public interface MomentQueryService {
    MomentResDTO.MomentPageDTO getMoments(Long userId, Long petId, Pageable pageable);
    MomentResDTO.MomentDTO getMoment(Long userId, Long petId, Long momentId);
}
