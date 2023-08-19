package com.side.tiggle.domain.member.service;

import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.member.repository.MemberRepository;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto createMember(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .profileUrl(memberDto.getProfileUrl())
                .nickname(memberDto.getNickname())
                .birth(memberDto.getBirth())
                .build();

        return memberDto.fromEntity(memberRepository.save(member));
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException());
    }

    public List<MemberDto> getAllMember() {
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            memberDtoList.add(MemberDto.fromEntity(member));
        }

        return memberDtoList;
    }

    public MemberDto updateMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException());

        member.setProfileUrl(memberDto.getProfileUrl());
        member.setNickname(memberDto.getNickname());
        member.setBirth(memberDto.getBirth());

        return memberDto.fromEntity(memberRepository.save(member));
    }

    // delete
}

