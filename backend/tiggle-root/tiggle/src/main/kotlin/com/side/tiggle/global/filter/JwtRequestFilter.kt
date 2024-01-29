package com.side.tiggle.global.filter

import com.side.tiggle.global.auth.JwtTokenProvider
import com.side.tiggle.global.exception.NotAuthenticatedException
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import com.side.tiggle.global.common.constants.HttpHeaders as CustomHeaders
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse
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
                throw NotAuthenticatedException("Invalid Token")
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

    /*
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1Iiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3MDM0OTE1MzMsImV4cCI6MTcwMzQ5MzMzM30.vxb0rr_sx8xGJ06Xh2vN-gPmNe_HWKYW6yeqH88hob8
     */

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