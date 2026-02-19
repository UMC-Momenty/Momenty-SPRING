package com.umc.momenty.domain.support.service.query;

import com.umc.momenty.domain.support.converter.AppConverter;
import com.umc.momenty.domain.support.dto.res.AppResDTO;
import com.umc.momenty.domain.support.entity.App;
import com.umc.momenty.domain.support.exception.AppException;
import com.umc.momenty.domain.support.exception.code.AppErrorCode;
import com.umc.momenty.domain.support.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppQueryServiceImpl implements AppQueryService {

    private final AppRepository appRepository;

    @Override
    public AppResDTO.AppDTO getApp(Long appId){

        App app = appRepository.findByIdAndActiveTrue(appId)
                .orElseThrow(() -> new AppException(AppErrorCode.APP_NOT_FOUND));

        return AppConverter.toAppDTO(app);
    }

    @Override
    public List<AppResDTO.AppListDTO> getAllApp(){
        return AppConverter.toAppListDTO(appRepository.findAllByActiveTrue());
    }
}
