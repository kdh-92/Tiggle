package com.side.tiggle.global.config

import com.side.tiggle.global.auth.jwt.JwtTokenProvider
import com.side.tiggle.global.auth.oauth2.OAuth2SuccessHandler
import com.side.tiggle.global.filter.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.security.config.annotation.web.invoke

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity, successHandler: OAuth2SuccessHandler): SecurityFilterChain {
        http {
            httpBasic { disable() }
            cors {
                configurationSource = corsConfigurationSource()
            }
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtRequestFilter)

            authorizeHttpRequests {
                authorize("/**", permitAll)
            }
            oauth2Login {
                authenticationSuccessHandler = successHandler
            }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
            .apply {
                allowedOriginPatterns = listOf("*")
                allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                allowedHeaders = listOf("*")
                exposedHeaders = listOf("*")
                allowCredentials = true
            }
        val source = UrlBasedCorsConfigurationSource()
            .apply {
                registerCorsConfiguration("/**", config)
            }

        return source
    }
}
