package com.umc.momenty.domain.support.entity;

import com.umc.momenty.domain.support.enums.InquiryCategory;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "inquiry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryCategory type;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "answer", length = 2000)
    private String answer;

    @Column(name = "is_answered", nullable = false)
    @Builder.Default
    private boolean isAnswered = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    @Builder.Default
    private List<InquiryImage> images = new ArrayList<>();

    public void addImage(InquiryImage image){
        images.add(image);
        image.assignInquiry(this);
    }
}
