package com.side.tiggle.domain.member.dto.req

import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate

data class MemberCreateReqDto(
    val email: String,
    val profileUrl: String,
    val nickname: String,
    val birth: LocalDate
) {
    fun toEntity(): Member {
        return Member(
            email = email,
            profileUrl = profileUrl,
            nickname = nickname,
            birth = birth
        )
    }
}
