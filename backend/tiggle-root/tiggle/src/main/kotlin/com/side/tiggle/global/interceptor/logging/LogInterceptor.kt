package com.side.tiggle.global.interceptor.logging

import com.side.tiggle.global.common.logging.KLog
import com.side.tiggle.global.common.logging.log
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@KLog
@Component
class LogInterceptor : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        try {
            initializeTraceId(request)
        } catch (e: Exception) {
            log.error("TraceId 생성 중 오류 발생", e)
        }
        return true
    }

    private fun initializeTraceId(request: HttpServletRequest) {
        val traceId = generateTraceId(request)
        setTraceId(traceId)
        log.info("TraceId 생성 성공 - $traceId")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        clearTraceId()
    }

    private fun generateTraceId(request: HttpServletRequest): String {
        val method = request.method
        val uri = request.requestURI.removePrefix("/").replace("/", "_")
        val uuid = UUID.randomUUID().toString().replace("-", "")

        return "${method}_${uri}_${uuid}"
    }

    private fun setTraceId(traceId: String) {
        MDC.put("traceId", traceId)
    }

    private fun clearTraceId() {
        MDC.clear()
    }
}

