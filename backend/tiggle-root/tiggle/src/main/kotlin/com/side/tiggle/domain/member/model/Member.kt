package com.side.tiggle.domain.member.model

import com.side.tiggle.global.common.model.BaseEntity
import java.time.LocalDate
import jakarta.persistence.*

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
}
