package com.side.tiggle.global.auth

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import com.side.tiggle.global.auth.oauth2.OAuth2Attribute
import com.side.tiggle.global.common.logging.KLog
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

@KLog
@Component
class OAuth2SuccessHandler(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,

    @Value("\${app.client-redirect-uri}")
    private val redirectUri: String
): SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val authenticationToken: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val authMember = getAndUpsert(authenticationToken)

        // 토큰 발급
        val token: String = jwtTokenProvider.getAccessToken(authMember.id, "ROLE_USER")

        // 토큰을 cookie에 담아서 보낸다
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
            if (profileUrl == null || profileUrl!!.startsWith("http")) {
                profileUrl = attributes.profileUrl
            }
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