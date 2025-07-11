package com.side.tiggle.global.filter

import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import com.side.tiggle.global.common.constants.HttpHeaders as CustomHeaders
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import kotlin.collections.HashMap


@Order(0)
class JwtRequestFilter(
        private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val excludeUrl = listOf(
            "/swagger-ui",
            "/static",
            "/favicon.ico",
            "/login",
            "/v3/api-docs"
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestWrapper = HeaderMapRequestWrapper(request)
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNullOrEmpty().not()) {
            val accessToken = authHeader.replace("Bearer ", "")
            if (jwtTokenProvider.isTokenValid(accessToken).not()) {
                throw AuthException(GlobalErrorCode.INVALID_TOKEN)
            }

            val memberId = jwtTokenProvider.getUserId(accessToken)
            logger.info("Member authenticated: id $memberId")

            requestWrapper.addHeader(CustomHeaders.MEMBER_ID, memberId.toString())
            filterChain.doFilter(requestWrapper, response)
        } else {
            requestWrapper.addHeader(CustomHeaders.MEMBER_ID, "-1")
            filterChain.doFilter(requestWrapper, response)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return excludeUrl.any {
            request.servletPath.startsWith(it)
        }
    }


    class HeaderMapRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
        private val headerMap: MutableMap<String, String> = HashMap()

        fun addHeader(name: String, value: String) {
            headerMap[name] = value
        }

        override fun getHeader(name: String): String? {
            var headerValue = super.getHeader(name)
            if (headerMap.containsKey(name)) {
                headerValue = headerMap[name]!!
            }
            return headerValue
        }

        override fun getHeaderNames(): Enumeration<String> {
            val names: MutableList<String> = Collections.list(super.getHeaderNames())
            for (name in headerMap.keys) {
                names.add(name)
            }
            return Collections.enumeration(names)
        }

        override fun getHeaders(name: String): Enumeration<String> {
            val values: MutableList<String?> = Collections.list(super.getHeaders(name))
            if (headerMap.containsKey(name)) {
                val header = headerMap[name]!!
                return Collections.enumeration(listOf(header))
            }
            return Collections.enumeration(values)
        }
    }

}