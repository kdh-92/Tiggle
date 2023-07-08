package com.side.tiggle.domain.member.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
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

    public Member(String email, String profileUrl, String nickname) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }
}
