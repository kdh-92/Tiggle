package com.side.tiggle.domain.member.repository

import com.side.tiggle.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}