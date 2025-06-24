package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.dto.resp.MemberListRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.model.Member
import org.springframework.web.multipart.MultipartFile

interface MemberService {

    fun createMember(dto: MemberCreateReqDto): MemberRespDto

    fun getMemberOrThrow(memberId: Long): Member

    fun getAllMember(): MemberListRespDto

    fun updateMember(memberId: Long, memberUpdateReqDto: MemberUpdateReqDto, file: MultipartFile?): MemberRespDto
}