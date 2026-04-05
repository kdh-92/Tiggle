package com.side.tiggle.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class SwaggerConfig(
    converter: MappingJackson2HttpMessageConverter
) {

    init {
        // application/octet-stream 도 지원하도록 설정
        val supportedMediaTypes = converter.supportedMediaTypes.toMutableList()
        supportedMediaTypes.add(MediaType("application", "octet-stream"))
        converter.supportedMediaTypes = supportedMediaTypes
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Tiggle API")
                    .version("1.0.0")
                    .description("Tiggle 백엔드 API 문서")
            )
            .servers(
                listOf(
                    Server().url("https://tiggle.duckdns.org:8180")
                        .description("배포 서버")
                )
            )
            .components(
                Components().addSecuritySchemes(
                    "bearer-key", SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}
