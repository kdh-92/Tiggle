package com.side.tiggle.domain.member.dto.controller

import java.time.LocalDate

data class MemberResponseDto(
    val id: Long,
    val email: String,
    val profileUrl: String?,
    val nickname: String,
    val birth: LocalDate?
) {
}
