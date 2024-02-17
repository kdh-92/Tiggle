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
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val memberRepository: MemberRepository
) {

    // TODO: @Value로 받아야 한다
    private val secret = "secrettigglesecrettigglesecrettiggle"
    private val secretKey: SecretKey

    private val tokenExpiry = 60L * 30L
    private val refreshTokenExpiry = 60L * 60L * 24L

    private val zone = ZoneId.systemDefault()

    init {
        secretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun generateToken(
        memberId: Long,
        roles: String,
        validFor: Long
    ): String {
        val issued = Date.from(LocalDateTime.now().atZone(zone).toInstant())
        val expiry = Date.from(LocalDateTime.now().plusSeconds(validFor).atZone(zone).toInstant())
        val claims = Jwts.claims().setSubject(memberId.toString())
        claims.put("roles", roles)

        return Jwts.builder()
            .setIssuer("tiggle")
            .setClaims(claims)
            .setIssuedAt(issued)
            .setExpiration(expiry)
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
        val expiry: Date = extractClaims(token).expiration
        return expiry.after(Date())
    }

    private fun extractClaims(
        jwtToken: String
    ): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJwt(jwtToken)
                .body
        } catch (e: JwtException) {
            throw IllegalStateException()
        }
    }

}