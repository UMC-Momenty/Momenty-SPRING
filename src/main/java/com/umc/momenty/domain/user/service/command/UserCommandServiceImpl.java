package com.umc.momenty.domain.user.service.command;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.global.jwt.JwtUtil;
import com.umc.momenty.global.oauth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private  final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public Optional<TokenDto> updateProfile(Long userId, UserReqDTO.UserProfileDTO userReqDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        String beforeRole = (user.getBirth() != null && user.getGender() != null)
                ? "ROLE_USER"
                : "ROLE_GUEST";

        Optional.ofNullable(userReqDTO.username())
                .filter(username -> !username.isBlank())
                .ifPresent(user::updateUsername);

        Optional.ofNullable(userReqDTO.gender())
                .ifPresent(user::updateGender);

        Optional.ofNullable(userReqDTO.birth())
                .ifPresent(user::updateBirth);

        Optional.ofNullable(userReqDTO.profileUrl())
                .ifPresent(profileUrl -> {
                    if (profileUrl.isBlank()) user.updateProfileUrl(null);
                    else user.updateProfileUrl(profileUrl);
                });

        if (Boolean.TRUE.equals(userReqDTO.resetQuestTime())) {
            user.updateQuestTime(null);
        } else {
            Optional.ofNullable(userReqDTO.questTime())
                    .ifPresent(user::updateQuestTime);
        }

        String afterRole = (user.getBirth() != null && user.getGender() != null)
                ? "ROLE_USER"
                : "ROLE_GUEST";

        if (!beforeRole.equals(afterRole)) {
            return Optional.of(jwtUtil.generateTokens(user.getId(), user.getSocialId(), afterRole));
        }

        return Optional.empty();
    }

}