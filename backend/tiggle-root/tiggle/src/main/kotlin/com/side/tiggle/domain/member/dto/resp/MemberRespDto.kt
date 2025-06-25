package com.side.tiggle.domain.member.dto.resp

import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate

data class MemberRespDto(
    val id: Long,
    val email: String,
    val profileUrl: String?,
    val nickname: String,
    val birth: LocalDate?
) {
    companion object {
//        @JvmStatic
        fun fromEntity(member: Member): MemberRespDto {
            return MemberRespDto(
                member.id,
                member.email,
                member.profileUrl,
                member.nickname,
                member.birth
            )
        }
    }
}
