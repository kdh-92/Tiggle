package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.internal.MemberInfo
import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.dto.resp.MemberListRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.exception.MemberException
import com.side.tiggle.domain.member.exception.error.MemberErrorCode
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.member.utils.FileUploadUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val fileUploadUtil: FileUploadUtil
) : MemberService {

    override fun createMember(dto: MemberCreateReqDto) {
        val member: Member = dto.toEntity()
        memberRepository.save(member)
    }

    override fun getMember(memberId: Long): MemberRespDto {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(MemberErrorCode.MEMBER_NOT_FOUND) }
        return MemberRespDto.fromEntity(member)
    }

    override fun getMemberOrThrow(memberId: Long): MemberInfo {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(MemberErrorCode.MEMBER_NOT_FOUND) }
        return MemberInfo.fromEntity(member)
    }

    override fun getAllMember(): MemberListRespDto {
        val members = memberRepository.findAll()
        val dtoList = members.map { MemberRespDto.fromEntity(it) }
        return MemberListRespDto(dtoList)
    }

    override fun updateMember(
        memberId: Long,
        memberUpdateReqDto: MemberUpdateReqDto,
        file: MultipartFile?
    ) {
        var isModified = false
        val member: Member = getMemberEntityOrThrow(memberId)

        if (file != null && !file.isEmpty) {
            member.profileUrl = fileUploadUtil.uploadProfileImage(memberId, file)
            isModified = true
        }

        if (memberUpdateReqDto.nickname != null) {
            member.nickname = memberUpdateReqDto.nickname
            isModified = true
        }

        if (memberUpdateReqDto.birth != null) {
            member.birth = memberUpdateReqDto.birth
            isModified = true
        }

        if (isModified) {
            memberRepository.save(member)
        }
    }

    private fun getMemberEntityOrThrow(memberId: Long): Member {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(MemberErrorCode.MEMBER_NOT_FOUND) }
        return member
    }
}
