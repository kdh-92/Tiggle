package com.side.tiggle.global.auth.oauth2

import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class OAuth2FailureHandler: SimpleUrlAuthenticationFailureHandler() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        logger.error("OAuth2 authentication failed: ${exception?.message}", exception)
        throw AuthException(GlobalErrorCode.OAUTH2_AUTHENTICATION_FAILED)
    }
}