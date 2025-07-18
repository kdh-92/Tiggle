package com.side.tiggle.global.auth

import com.side.tiggle.global.auth.dto.TokenResponse
import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import com.side.tiggle.global.auth.jwt.RefreshTokenService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService
) {

    @PostMapping("/refresh")
    @Operation(
        summary = "토큰 갱신",
        description = "Refresh-Token 헤더를 사용하여 새로운 Access Token을 발급받습니다"
    )
    @Parameter(
        name = "Refresh-Token",
        description = "리프레시 토큰",
        required = true,
        `in` = ParameterIn.HEADER,
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    fun refreshToken(request: HttpServletRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val refreshToken = jwtTokenProvider.resolveRefreshToken(request)
            ?: throw AuthException(GlobalErrorCode.INVALID_REFRESH_TOKEN)

        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw AuthException(GlobalErrorCode.INVALID_REFRESH_TOKEN)
        }

        val memberId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken)

        val newAccessToken = jwtTokenProvider.getAccessToken(memberId, "ROLE_USER")

        val newRefreshToken = jwtTokenProvider.getRefreshToken(memberId, "ROLE_USER")
        refreshTokenService.saveRefreshToken(memberId, newRefreshToken)

        val tokenResponse = TokenResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )

        return ResponseEntity.ok(ApiResponse.success(tokenResponse, "토큰이 성공적으로 갱신되었습니다"))
    }

    @PostMapping("/logout")
    @Operation(
        summary = "로그아웃",
        description = "현재 사용자의 Refresh Token을 무효화합니다",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    fun logout(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        if (memberId == -1L) {
            throw AuthException(GlobalErrorCode.NOT_AUTHENTICATED)
        }

        refreshTokenService.deleteRefreshToken(memberId)

        return ResponseEntity.ok(ApiResponse.success(Unit, "로그아웃이 완료되었습니다"))
    }

    @GetMapping("/validate")
    @Operation(
        summary = "토큰 유효성 검증",
        description = "현재 Access Token의 유효성을 확인합니다",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    fun validateToken(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<Map<String, Any>>> {
        if (memberId == -1L) {
            throw AuthException(GlobalErrorCode.NOT_AUTHENTICATED)
        }

        val response = mapOf(
            "valid" to true,
            "memberId" to memberId
        )

        return ResponseEntity.ok(ApiResponse.success(response, "토큰이 유효합니다"))
    }
}