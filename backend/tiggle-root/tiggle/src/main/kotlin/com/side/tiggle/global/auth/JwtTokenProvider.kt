package com.side.tiggle.global.auth

import com.side.tiggle.domain.member.repository.MemberRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import jakarta.servlet.http.HttpServletRequest
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val memberRepository: MemberRepository
) {

    // TODO: @Value로 받아야 한다
    private val secret = "secrettigglesecrettigglesecrettiggle"
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    private val tokenExpiry = 60L * 30L
    private val refreshTokenExpiry = 60L * 60L * 24L

    private val zone = ZoneId.systemDefault()

    fun generateToken(
        memberId: Long,
        roles: String,
        validFor: Long
    ): String {
        val issued = Date.from(LocalDateTime.now().atZone(zone).toInstant())
        val expiry = Date.from(LocalDateTime.now().plusSeconds(validFor).atZone(zone).toInstant())

        return Jwts.builder()
            .issuer("tiggle")
            .subject(memberId.toString())
            .claim("roles", roles)
            .issuedAt(issued)
            .expiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    fun getAccessToken(
        memberId: Long,
        roles: String
    ): String {
        return generateToken(memberId, roles, tokenExpiry)
    }

    fun getRefreshToken(
        memberId: Long,
        roles: String
    ): String {
        return generateToken(memberId, roles, refreshTokenExpiry)
    }

    fun resolveAccessToken(request: HttpServletRequest): String {
        return request.getHeader("Authorization").substring("Bearer ".length)
    }

    fun resolveRefreshToken(request: HttpServletRequest): String {
        return request.getHeader("Refresh")
    }

    fun getUserId(token: String): Long {
        return extractClaims(token).subject.toLong()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            val expiry: Date = extractClaims(token).expiration
            expiry.after(Date())
        } catch (e: Exception) {
            println("토큰 검증 실패: ${e.message}")
            false
        }
    }

    private fun extractClaims(
        jwtToken: String
    ): Claims {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken)
                .payload
        } catch (e: JwtException) {
            throw IllegalStateException()
        }
    }

}