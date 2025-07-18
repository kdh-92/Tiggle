package com.side.tiggle.global.filter

import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import jakarta.servlet.FilterChain
import org.springframework.http.MediaType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import com.side.tiggle.global.common.constants.HttpHeaders as CustomHeaders
import com.side.tiggle.global.exception.error.GlobalErrorCode
import com.fasterxml.jackson.databind.ObjectMapper


@Order(0)
class JwtRequestFilter(
        private val jwtTokenProvider: JwtTokenProvider,
        private val objectMapper: ObjectMapper
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
                val errorCode = GlobalErrorCode.INVALID_TOKEN
                response.status = errorCode.httpStatus().value()
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                response.characterEncoding = "UTF-8"

                val errorResponse = mapOf(
                    "success" to false,
                    "code" to errorCode.codeNumber().toString(),
                    "message" to errorCode.message()
                )

                response.writer.write(objectMapper.writeValueAsString(errorResponse))
                response.writer.flush()
                return
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