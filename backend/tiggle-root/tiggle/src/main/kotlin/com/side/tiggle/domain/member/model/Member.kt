package com.side.tiggle.domain.member.model

import com.side.tiggle.global.common.model.BaseEntity
import java.time.LocalDate
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "members")
//@JsonIgnoreProperties(["hibernateLazyInitializer", "handler"])
class Member (
    var email: String,
    var profileUrl: String?,
    var nickname: String,
    var birth: LocalDate? = null,
    var provider: String? = null,
    var providerId: String? = null
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "refresh_token", length = 500)
    var refreshToken: String? = null

    @Column(name = "refresh_token_expires_at")
    var refreshTokenExpiresAt: LocalDateTime? = null
}
