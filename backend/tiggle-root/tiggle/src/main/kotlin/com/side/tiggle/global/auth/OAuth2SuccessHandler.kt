package com.side.tiggle.global.auth

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
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

    @Value("\${app.client-redirect-uri}")
    private val redirectUri: String
): SimpleUrlAuthenticationSuccessHandler() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val authenticationToken: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val authMember = getAndUpsert(authenticationToken)

        val token: String = jwtTokenProvider.getAccessToken(authMember.id, "ROLE_USER")

        val cookie = Cookie("Authorization", token)
        cookie.maxAge = 60000
        cookie.path = "/" // FIXME: 특정 경로에서만 사용 가능하도록 수정한다

        response.addCookie(cookie)
        response.sendRedirect(redirectUri)
    }

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