package com.umc.momenty.domain.user.dto.req;

import com.umc.momenty.domain.user.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserReqDTO {

    @Builder
    public record UserProfileDTO(
            String username,
            Gender gender,
            LocalDate birth,
            String profileUrl,
            LocalTime questTime
    ){}
}
