package com.side.tiggle.domain.member.dto.req

import java.time.LocalDate

data class MemberUpdateReqDto(
    val nickname: String?,
    val birth: LocalDate?
)
