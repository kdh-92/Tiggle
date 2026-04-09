package com.side.tiggle.domain.challenge.repository

import com.side.tiggle.domain.challenge.model.Challenge
import com.side.tiggle.domain.challenge.model.ChallengeStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeRepository : JpaRepository<Challenge, Long> {

    fun findByMemberIdAndStatus(memberId: Long, status: ChallengeStatus): Challenge?

    fun findAllByMemberIdOrderByCreatedAtDesc(memberId: Long, pageable: Pageable): Page<Challenge>

    fun findAllByStatus(status: ChallengeStatus): List<Challenge>
}
