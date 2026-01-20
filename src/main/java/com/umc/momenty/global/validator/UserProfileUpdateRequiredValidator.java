package com.umc.momenty.global.validator;

import com.umc.momenty.domain.user.dto.req.UserReqDTO;
import com.umc.momenty.global.annotation.UserProfileUpdateRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserProfileUpdateRequiredValidator implements ConstraintValidator<UserProfileUpdateRequired, UserReqDTO.UserProfileDTO> {

    @Override
    public boolean isValid(UserReqDTO.UserProfileDTO dto,
                           ConstraintValidatorContext context) {

        if (dto == null) return false;

        boolean hasUpdateField = Stream.of(
                dto.username(),
                dto.gender(),
                dto.birth(),
                dto.profileUrl(),
                dto.questTime()
        ).anyMatch(Objects::nonNull);

        boolean resetQuestTime = Boolean.TRUE.equals(dto.resetQuestTime());

        return hasUpdateField || resetQuestTime;
    }
}
