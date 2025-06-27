package com.side.tiggle.domain.member.dto.req

import jakarta.validation.constraints.Size
import java.time.LocalDate

data class MemberUpdateReqDto(

    @field:Size(max = 30, message = "닉네임은 30자 이하여야 합니다")
    val nickname: String?,
    val birth: LocalDate?
)
