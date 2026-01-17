package com.umc.momenty.domain.user.entity;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.enums.AuthProvider;
import com.umc.momenty.domain.user.enums.Gender;
import com.umc.momenty.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "quest_time")
    private LocalTime questTime;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user")
    private List<Pet> pets = new ArrayList<>();

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateQuestTime(LocalTime questTime) {
        this.questTime = questTime;
    }
}
