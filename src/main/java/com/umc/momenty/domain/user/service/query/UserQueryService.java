package com.umc.momenty.domain.user.service.query;

import com.umc.momenty.domain.user.dto.res.UserResDTO;

public interface UserQueryService {

    UserResDTO.UserProfileDTO getUserProfile(Long userId);
}
