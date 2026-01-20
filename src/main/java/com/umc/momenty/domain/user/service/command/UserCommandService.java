package com.umc.momenty.domain.user.service.command;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;

public interface UserCommandService {

    void updateUserProfile(Long userId, UserReqDTO.UserProfileDTO userReqDTO);
}
