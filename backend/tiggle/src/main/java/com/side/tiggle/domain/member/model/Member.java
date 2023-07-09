package com.side.tiggle.domain.member.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Column(name = "profile_url")
    private String profileUrl;
    private String nickname;
    private LocalDate birth;

    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Builder
    public Member(String email, String profileUrl, String nickname) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }
}


