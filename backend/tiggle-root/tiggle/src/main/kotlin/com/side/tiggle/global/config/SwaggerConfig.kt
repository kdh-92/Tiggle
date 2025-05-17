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
        val supportedMediaTypes = ArrayList(converter.supportedMediaTypes)
        supportedMediaTypes.add(MediaType("application", "octet-stream"))
        converter.supportedMediaTypes = supportedMediaTypes
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
        .servers(
                listOf(
                    Server().url("https://tiggle.duckdns.org:8180")
                            )
                        )
            .components(Components()
                .addSecuritySchemes("bearer-key", SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
            )
            .info(Info().title("Tiggle").version("1.0.0"))
    }
}
