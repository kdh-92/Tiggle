package com.side.tiggle.global.auth.jwt

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RefreshTokenServiceImpl(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider
) : RefreshTokenService {

    @Value("\${jwt.refresh-token-expiry:604800}")  // 추가
    private val refreshTokenExpirySeconds: Long = 0

    @Transactional
    override fun saveRefreshToken(memberId: Long, refreshToken: String) {
        val member = memberRepository.findById(memberId)
            .orElseThrow { AuthException(GlobalErrorCode.NOT_AUTHENTICATED) }

        val expiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpirySeconds)

        member.refreshToken = refreshToken
        member.refreshTokenExpiresAt = expiresAt

        memberRepository.save(member)
    }

    override fun validateRefreshToken(refreshToken: String): Boolean {
        if (!jwtTokenProvider.isTokenValid(refreshToken)) {
            return false
        }

        val member = memberRepository.findByRefreshToken(refreshToken)
            ?: return false

        val now = LocalDateTime.now()
        return member.refreshTokenExpiresAt?.isAfter(now) == true
    }

    @Transactional
    override fun deleteRefreshToken(memberId: Long) {
        val member = memberRepository.findById(memberId)
            .orElseThrow { AuthException(GlobalErrorCode.NOT_AUTHENTICATED) }

        member.refreshToken = null
        member.refreshTokenExpiresAt = null

        memberRepository.save(member)
    }

    override fun findMemberByRefreshToken(refreshToken: String): Member? {
        return memberRepository.findByRefreshToken(refreshToken)
    }
}