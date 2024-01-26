package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.member.dto.service.MemberDto
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    private val FOLDER_PATH: String = System.getProperty("user.dir") + "/upload/profile";

    fun createMember(memberDto: MemberDto): MemberResponseDto {
        val member = Member(
            memberDto.email,
            memberDto.profileUrl,
            memberDto.nickname,
            memberDto.birth
        )
        return MemberDto.fromEntityToMemberResponseDto(memberRepository.save(member))
    }

    fun getMember(memberId: Long): MemberResponseDto {
        return MemberDto.fromEntityToMemberResponseDto(
            memberRepository.findById(memberId).orElseThrow{ NotFoundException() }
        )
    }

    fun getAllMember(): List<MemberResponseDto> {
        return memberRepository.findAll()
            .map { MemberDto.fromEntityToMemberResponseDto(it) }
    }

    fun updateMember(memberId: Long, memberDto: MemberDto, file: MultipartFile?): MemberResponseDto {
        val member: Member = memberRepository.findById(memberId)
            .orElseThrow { NotFoundException() }
        if (file != null && !file.isEmpty) {
            member.profileUrl = uploadProfile(memberId, file)
        }

        member.nickname = memberDto.nickname
        member.birth = memberDto.birth

        return MemberDto.fromEntityToMemberResponseDto(memberRepository.save(member))
    }

    fun uploadProfile(memberId: Long, file: MultipartFile): String {
        val uploadFolder = File(FOLDER_PATH, memberId.toString())
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }
        val folderPath: String = uploadFolder.absolutePath;
        val savePath: Path = Paths.get(folderPath + File.separator + file.originalFilename);

        file.transferTo(savePath);

        return savePath.toString();
    }
}