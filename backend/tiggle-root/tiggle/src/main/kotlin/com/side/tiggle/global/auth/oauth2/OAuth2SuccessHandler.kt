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

    @Value("\${jwt.access-token-expiry}")
    private val accessTokenExpiry: Long,

    @Value("\${jwt.refresh-token-expiry}")
    private val refreshTokenExpiry: Long,

    @Value("\${app.client-redirect-uri}")
    private val redirectUri: String
): SimpleUrlAuthenticationSuccessHandler() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * OAuth2 인증 성공 시 토큰 발급 및 리다이렉트를 처리합니다.
     *
     * 1. OAuth2 인증 정보에서 사용자 정보 추출
     * 2. 데이터베이스에 사용자 정보 저장/업데이트
     * 3. Access Token 및 Refresh Token 발급
     * 4. 토큰을 HttpOnly 쿠키로 설정 (TODO)
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

        val accessCookie = Cookie("Authorization", accessToken)
        accessCookie.maxAge = accessTokenExpiry.toInt()
        accessCookie.path = "/"

        val refreshCookie = Cookie("RefreshToken", refreshToken)
        refreshCookie.maxAge = refreshTokenExpiry.toInt()
        refreshCookie.path = "/"

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
            updateProfileIfNeeded(attributes.profileUrl)
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

    /**
     * 커스텀 프로필 이미지가 있으면 유지하고, OAuth2 이미지만 업데이트합니다.
     *
     * 1. 프로필 업데이트 필요성 확인
     * 2. 필요한 경우에만 OAuth2 프로필 URL로 업데이트
     * 3. 커스텀 업로드 이미지는 보존
     *
     * @param newProfileUrl OAuth2 제공자에서 받은 새로운 프로필 URL
     * @since 2025-07-19 최초 작성
     * @author 양병학
     */
    private fun Member.updateProfileIfNeeded(newProfileUrl: String?) {
        if (shouldUpdateProfile()) {
            profileUrl = newProfileUrl
        }
    }

    /**
     * 프로필 이미지를 업데이트해야 하는지 판단합니다.
     *
     * 1. 프로필이 비어있는지 확인
     * 2. 현재 프로필이 OAuth2 이미지인지 확인
     * 3. 둘 중 하나라도 true면 업데이트 필요
     *
     * @return true: OAuth2 이미지로 업데이트 필요, false: 기존 커스텀 이미지 유지
     * @since 2025-07-19 최초 작성
     * @author 양병학
     */
    private fun Member.shouldUpdateProfile(): Boolean {
        return isProfileEmpty() || isOAuth2Profile()
    }

    /**
     * 프로필 이미지가 없는 상태인지 확인합니다.
     *
     * @return true: 프로필 URL이 null인 경우, false: 프로필 URL이 존재하는 경우
     * @since 2025-07-19 최초 작성
     * @author 양병학
     */
    private fun Member.isProfileEmpty(): Boolean {
        return profileUrl == null
    }

    /**
     * 현재 프로필이 OAuth2에서 가져온 이미지인지 확인합니다.
     *
     * OAuth2 이미지와 커스텀 이미지를 구분하는 기준:
     * - OAuth2 이미지: http/https로 시작하는 외부 URL
     * - 커스텀 이미지: 우리 서버의 로컬 파일 경로
     *
     * @return true: OAuth2 외부 이미지인 경우, false: 커스텀 업로드 이미지인 경우
     * @since 2025-07-19 최초 작성
     * @author 양병학
     */
    private fun Member.isOAuth2Profile(): Boolean {
        return profileUrl != null && profileUrl!!.startsWith("http")
    }
}