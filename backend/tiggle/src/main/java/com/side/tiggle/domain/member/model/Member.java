package com.side.tiggle.domain.member.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Column(name = "profile_url")
    private String profileUrl;
    private String nickname;
    private LocalDate birth;

    public Member(String email, String profileUrl) {
        this.email = email;
        this.profileUrl = profileUrl;
    }
}
