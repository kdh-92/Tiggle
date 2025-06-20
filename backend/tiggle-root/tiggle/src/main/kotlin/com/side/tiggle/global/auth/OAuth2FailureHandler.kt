package com.side.tiggle.global.auth

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
        logger.error(exception?.message)
        logger.error("error: ${exception?.message}", exception)
    }
}