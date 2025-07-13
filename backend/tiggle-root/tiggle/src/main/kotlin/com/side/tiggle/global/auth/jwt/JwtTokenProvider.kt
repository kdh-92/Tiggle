package com.side.tiggle.global.auth.jwt

import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode
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

    private val tokenExpiry = 60L * 60L * 24L
    private val refreshTokenExpiry = 60L * 60L * 24L * 7L

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
        val authHeader = request.getHeader("Authorization")
            ?: throw AuthException(GlobalErrorCode.NOT_AUTHENTICATED)

        if (!authHeader.startsWith("Bearer ")) {
            throw AuthException(GlobalErrorCode.INVALID_TOKEN)
        }

        return authHeader.substring("Bearer ".length)
    }

    fun resolveRefreshToken(request: HttpServletRequest): String? {
        return request.getHeader("Refresh-Token")
    }

    fun getUserId(token: String): Long {
        return extractClaims(token).subject.toLong()
    }

    fun getUserIdFromRefreshToken(token: String): Long {
        return extractClaims(token).subject.toLong()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            val expiry: Date = extractClaims(token).expiration
            expiry.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun validateRefreshToken(token: String): Boolean {
        return try {
            if (!isTokenValid(token)) {
                return false
            }

            val member = memberRepository.findByRefreshToken(token)
                ?: return false

            val now = LocalDateTime.now()
            member.refreshTokenExpiresAt?.isAfter(now) == true
        } catch (e: Exception) {
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
            throw AuthException(GlobalErrorCode.INVALID_TOKEN, e)
        }
    }
}