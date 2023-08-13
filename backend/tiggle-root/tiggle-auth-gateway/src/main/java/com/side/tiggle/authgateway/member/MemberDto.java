package com.side.tiggle.authgateway.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {
    long id;
    String email;
    String profileUrl;
    String nickname;
    LocalDate birth;

    public static MemberDto fromEntity(Member member) {
        MemberDto dto = new MemberDto();
        dto.id = member.getId();
        dto.birth = member.getBirth();
        dto.email = member.getEmail();
        dto.nickname = member.getNickname();
        dto.profileUrl = member.getProfileUrl();

        return dto;
    }
}
