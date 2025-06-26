package com.side.tiggle.global.config

import com.side.tiggle.global.interceptor.logging.LogInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val logInterceptor: LogInterceptor
) : WebMvcConfigurer {

    companion object {
        private const val ALL_PATTERN = "/**"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(logInterceptor)
            .addPathPatterns(ALL_PATTERN)
    }
}

