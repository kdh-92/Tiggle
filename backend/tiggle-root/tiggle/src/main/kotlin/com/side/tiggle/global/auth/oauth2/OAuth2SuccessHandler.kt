package com.side.tiggle.global.auth.oauth2

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import com.side.tiggle.global.auth.jwt.RefreshTokenService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class OAuth2SuccessHandler(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService,

    @Value("\${app.client-redirect-uri}")
    private val redirectUri: String
): SimpleUrlAuthenticationSuccessHandler() {

    companion object {
        private const val ACCESS_TOKEN_COOKIE_MAX_AGE = 60 * 30 // 30분
        private const val REFRESH_TOKEN_COOKIE_MAX_AGE = 60 * 60 * 24 * 7 // 7일
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * OAuth2 인증 성공 시 토큰 발급 및 리다이렉트를 처리합니다.
     *
     * 1. OAuth2 인증 정보에서 사용자 정보 추출
     * 2. 데이터베이스에 사용자 정보 저장/업데이트
     * 3. Access Token 및 Refresh Token 발급
     * 4. 토큰을 HttpOnly 쿠키로 설정
     * 5. 클라이언트로 리다이렉트
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param authentication OAuth2 인증 정보
     * @since 2025-07-12
     * @author 양병학
     */
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val authenticationToken: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val authMember = getAndUpsert(authenticationToken)

        val accessToken: String = jwtTokenProvider.getAccessToken(authMember.id, "ROLE_USER")

        val refreshToken: String = jwtTokenProvider.getRefreshToken(authMember.id, "ROLE_USER")
        refreshTokenService.saveRefreshToken(authMember.id, refreshToken)

        // Access Token을 쿠키에 담아서 보낸다
        val accessCookie = Cookie("Authorization", accessToken)
        accessCookie.maxAge = ACCESS_TOKEN_COOKIE_MAX_AGE
        accessCookie.path = "/"
        accessCookie.isHttpOnly = true
        // accessCookie.secure = true // HTTPS 환경에서만 활성화

        val refreshCookie = Cookie("RefreshToken", refreshToken)
        refreshCookie.maxAge = REFRESH_TOKEN_COOKIE_MAX_AGE
        refreshCookie.path = "/"
        refreshCookie.isHttpOnly = true
        // refreshCookie.secure = true // HTTPS 환경에서만 활성화

        response.addCookie(accessCookie)
        response.addCookie(refreshCookie)
        response.sendRedirect(redirectUri)
    }

    /**
     * OAuth2 인증 정보를 기반으로 사용자를 조회하거나 신규 생성합니다.
     *
     * 1. OAuth2 사용자 정보 추출
     * 2. 이메일로 기존 사용자 조회
     * 3. 기존 사용자인 경우 프로필 정보 업데이트
     * 4. 신규 사용자인 경우 새로운 Member 생성
     * 5. 데이터베이스에 저장
     *
     * @param authenticationToken OAuth2 인증 토큰
     * @return 저장된 Member 엔티티
     * @since 2025-07-12
     * @author 양병학
     */
    private fun getAndUpsert(authenticationToken: OAuth2AuthenticationToken): Member {
        val userPrincipal = authenticationToken.principal as DefaultOAuth2User
        logger.info("oAuth2User: $userPrincipal")

        val attributes = OAuth2Attribute.of(
            authenticationToken.authorizedClientRegistrationId,
            userPrincipal.name,
            userPrincipal.attributes
        )
        val member = memberRepository.findByEmail(attributes.email)
        val authMember: Member = member?.apply {
            profileUrl = attributes.profileUrl
            provider = authenticationToken.authorizedClientRegistrationId
        }
            ?: Member(
                email = attributes.email,
                profileUrl = attributes.profileUrl,
                nickname = attributes.nickname,
                provider = authenticationToken.authorizedClientRegistrationId.toString(),
                providerId = attributes.providerId
            )
        memberRepository.save(authMember)
        return authMember
    }
}