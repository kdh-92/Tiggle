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

    override fun createMember(dto: MemberCreateReqDto): MemberRespDto {
        val member: Member = dto.toEntity()
        val savedMember = memberRepository.save(member)
        return MemberRespDto.fromEntity(savedMember)
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
    ): MemberRespDto {
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

        val updatedMember = if (isModified) {
            memberRepository.save(member)
        } else {
            member
        }

        return MemberRespDto.fromEntity(updatedMember)
    }

    private fun getMemberEntityOrThrow(memberId: Long): Member {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberException(MemberErrorCode.MEMBER_NOT_FOUND) }
        return member
    }

    /**
     * 댓글 생성 시 연관관계 설정을 위한 Member 프록시 객체를 반환합니다.
     *
     * 실제 Member 엔티티를 조회하지 않고 JPA 프록시 객체만 생성하여
     * 연관관계 설정 시 성능을 최적화합니다.
     *
     * @param memberId 회원 ID
     * @return Member 프록시 객체 (실제 DB 조회 없이 연관관계 설정용)
     * @since 2025-07-04
     * @author 양병학
     */
    override fun getMemberReference(memberId: Long): Member {
        return memberRepository.getReferenceById(memberId)
    }
}

