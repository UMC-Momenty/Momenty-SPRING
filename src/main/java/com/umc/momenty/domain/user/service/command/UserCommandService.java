package com.umc.momenty.domain.user.service.command;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.global.oauth.dto.TokenDto;

import java.util.Optional;

public interface UserCommandService {

    Optional<TokenDto>updateProfile(Long userId, UserReqDTO.UserProfileDTO userReqDTO);
}
