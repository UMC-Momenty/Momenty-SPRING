package com.umc.momenty.domain.user.dto.req;

import com.umc.momenty.domain.user.enums.Gender;
import com.umc.momenty.global.annotation.UserProfileUpdateRequired;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserReqDTO {

    @UserProfileUpdateRequired
    @Builder
    public record UserProfileDTO(
            String username,
            Gender gender,
            LocalDate birth,
            String profileUrl,
            LocalTime questTime,
            Boolean resetQuestTime
    ){}
}
