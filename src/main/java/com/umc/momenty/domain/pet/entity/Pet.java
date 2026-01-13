package com.umc.momenty.domain.pet.entity;

import com.umc.momenty.domain.pet.enums.PetGender;
import com.umc.momenty.domain.pet.enums.Species;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "pet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetGender gender;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "species", nullable = false)
    @Enumerated(EnumType.STRING)
    private Species species;

    @Column(name = "intro", length = 500)
    private String intro;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;
}
