package com.side.tiggle.global.config

import com.side.tiggle.global.interceptor.logging.LogInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val logInterceptor: LogInterceptor,
    @Value("\${part.upload.path}") private val uploadBasePath: String
) : WebMvcConfigurer {

    companion object {
        private const val ALL_PATTERN = "/**"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(logInterceptor)
            .addPathPatterns(ALL_PATTERN)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/upload/**")
            .addResourceLocations("file:$uploadBasePath")
            .setCachePeriod(3600)
    }
}

