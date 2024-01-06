package com.side.tiggle.tigglenotification.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
class SwaggerConfig(private val converter: MappingJackson2HttpMessageConverter) {

    init {
        val supportedMediaTypes = ArrayList(converter.supportedMediaTypes)
        supportedMediaTypes.add(MediaType("application", "octet-stream"))
        converter.supportedMediaTypes = supportedMediaTypes
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(Info().title("Tiggle").version("1.0.0"))
    }
}
