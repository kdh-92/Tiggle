package com.side.tiggle.domain.member;

import com.side.tiggle.domain.member.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
