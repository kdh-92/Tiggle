package com.side.tiggle.domain.member.service;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.repository.MemberRepository;
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

        Member createdMember = memberRepository.save(member);

        return memberDto.fromEntity(createdMember);
    }

    public MemberDto getMember(Long memberId) {
        return MemberDto.fromEntity(memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<MemberDto> getAllMember() {
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            memberDtos.add(MemberDto.fromEntity(member));
        }

        return memberDtos;
    }

    public MemberDto updateMember(Long id, MemberDto memberDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        member.setProfileUrl(memberDto.getProfileUrl());
        member.setNickname(memberDto.getNickname());
        member.setBirth(memberDto.getBirth());

        return memberDto.fromEntity(memberRepository.save(member));
    }
}

