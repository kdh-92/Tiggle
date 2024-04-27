package com.side.tiggle.domain.member.dto.controller

import com.side.tiggle.domain.member.dto.service.MemberDto
import java.time.LocalDate

data class MemberUpdateReqDto(
    val nickname: String?,
    val birth: LocalDate?
)