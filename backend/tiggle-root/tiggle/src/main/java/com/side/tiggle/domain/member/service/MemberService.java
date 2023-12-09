package com.side.tiggle.domain.member.service;

import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.member.repository.MemberRepository;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final String FOLDER_PATH = System.getProperty("user.dir") + "/upload/profile";

    public MemberDto createMember(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .profileUrl(memberDto.getProfileUrl())
                .nickname(memberDto.getNickname())
                .birth(memberDto.getBirth())
                .build();

        return MemberDto.fromEntity(memberRepository.save(member));
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);
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
                .orElseThrow(NotFoundException::new);

        member.setProfileUrl(memberDto.getProfileUrl());
        member.setNickname(memberDto.getNickname());
        member.setBirth(memberDto.getBirth());

        return MemberDto.fromEntity(memberRepository.save(member));
    }

    public MemberDto updateMember(Long memberId, MemberDto memberDto, MultipartFile file) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);
        if (file != null && !file.isEmpty()) {
            String profileUrl = uploadProfile(memberId, file);
            member.setProfileUrl(profileUrl);
        }
        if (memberDto.getNickname() != null) {
            member.setNickname(memberDto.getNickname());
        }
        if (memberDto.getBirth() != null) {
            member.setBirth(memberDto.getBirth());
        }

        return MemberDto.fromEntity(memberRepository.save(member));
    }

    private String uploadProfile(Long memberId, MultipartFile file) throws IOException {
        File uploadFolder = new File(FOLDER_PATH, String.valueOf(memberId));
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        String folderPath = uploadFolder.getAbsolutePath();
        Path savePath = Paths.get(folderPath + File.separator + file.getOriginalFilename());
        file.transferTo(savePath);

        return savePath.toString();
    }
    // delete
}

