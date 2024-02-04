//package com.side.tiggle.global.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import java.util.ArrayList;
//
//
//@Configuration
//public class SwaggerConfig {
//    public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
//        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
//        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
//        converter.setSupportedMediaTypes(supportedMediaTypes);
//    }
//
//    @Bean
//    public OpenAPI openAPI(){
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
//                )
//                .info(new Info().title("Tiggle").version("1.0.0"));
//    }
//}
