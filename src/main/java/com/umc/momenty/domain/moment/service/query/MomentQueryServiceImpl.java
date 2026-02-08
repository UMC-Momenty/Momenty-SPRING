package com.umc.momenty.domain.moment.service.query;

import com.umc.momenty.domain.moment.converter.MomentConverter;
import com.umc.momenty.domain.moment.dto.res.MomentResDTO;
import com.umc.momenty.domain.moment.entity.Moment;
import com.umc.momenty.domain.moment.exception.MomentException;
import com.umc.momenty.domain.moment.exception.code.MomentErrorCode;
import com.umc.momenty.domain.moment.repository.MomentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MomentQueryServiceImpl implements MomentQueryService{

    private final MomentRepository momentRepository;

    @Override
    @Transactional(readOnly = true)
    public MomentResDTO.MomentPageDTO getMoments(Long userId, Long petId, Pageable pageable) {
        Page<Moment> page = momentRepository.findAllByUserIdAndPetId(userId, petId, pageable);
        return MomentConverter.toMomentPageDTO(page);
    }

    @Override
    @Transactional
    public MomentResDTO.MomentDTO getMoment(Long momentId){
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new MomentException(MomentErrorCode.MOMENT_NOT_FOUND));
        return MomentConverter.toMomentDTO(moment);
    }
}
