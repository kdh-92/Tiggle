package com.side.tiggle.domain.member.dto.service

import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate

data class MemberDto(
    val id: Long?,
    val email: String,
    val profileUrl: String?,
    val nickname: String,
    val birth: LocalDate?
) {
    companion object {
        @JvmStatic
        fun fromEntityToMemberResponseDto(member: Member): MemberResponseDto {
            return MemberResponseDto(member.id, member.email, member.profileUrl, member.nickname, member.birth)
        }
    }
}