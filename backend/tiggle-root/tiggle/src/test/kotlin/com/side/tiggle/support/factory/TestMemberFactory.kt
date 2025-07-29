package com.side.tiggle.support.factory

import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate

object TestMemberFactory {
    fun create(
        id: Long? = null,
        nickname: String = "test",
        email: String = "test@example.com",
        profileUrl: String = "https://profile.com",
        birth: LocalDate = LocalDate.of(2000, 1, 1),
        provider: String = "google",
        providerId: String = "google_123"
    ): Member {
        val member = Member(
            nickname = nickname,
            email = email,
            profileUrl = profileUrl,
            birth = birth,
            provider = provider,
            providerId = providerId
        )

        // 리플렉션으로 id 주입 (필요한 경우만)
        if (id != null) {
            val field = Member::class.java.getDeclaredField("id")
            field.isAccessible = true
            field.set(member, id)
        }

        return member
    }
}
