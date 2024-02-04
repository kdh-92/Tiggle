package com.side.tiggle.domain.member.dto.controller

import com.side.tiggle.domain.member.dto.service.MemberDto
import java.time.LocalDate

data class MemberRequestDto(
    val email: String,
    val profileUrl: String,
    val nickname: String,
    val birth: LocalDate
) {
    companion object {
        fun fromMemberRequestDtoToMemberDto(dto: MemberRequestDto): MemberDto {
            return MemberDto(null, dto.email, dto.profileUrl, dto.nickname, dto.birth)
        }
    }
}