package com.side.tiggle.domain.member.dto.req

import com.side.tiggle.domain.member.model.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class MemberCreateReqDto(

    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    @field:Size(max = 100, message = "이메일은 100자 이하여야 합니다")
    val email: String,

    @field:Size(max = 255, message = "프로필 URL은 255자 이하여야 합니다")
    val profileUrl: String,

    @field:NotBlank(message = "닉네임은 필수입니다")
    @field:Size(max = 30, message = "닉네임은 30자 이하여야 합니다")
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
