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
        val traceId = generateTraceId(request)
        setTraceId(traceId)
        log.info("TraceId 생성 성공 - $traceId")
        return true
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
        val uri = request.requestURI.replace("/", "-")
        val timestamp = System.currentTimeMillis().toString().takeLast(6)

        return "${method}${uri}-${timestamp}"
    }

    private fun setTraceId(traceId: String) {
        MDC.put("traceId", traceId)
    }

    private fun clearTraceId() {
        MDC.clear()
    }
}