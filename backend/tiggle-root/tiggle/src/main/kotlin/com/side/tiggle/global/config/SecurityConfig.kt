package com.side.tiggle.global.config

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
                authorize("/api/v1/member", permitAll)
                authorize("/api/v1/member/{id}", permitAll)
                authorize("/api/v1/member/all", permitAll)
                authorize("/api/v1/transaction", permitAll)
                authorize("/api/v1/transaction/{id}", permitAll)
                authorize("/api/v1/transaction/search", permitAll)
                authorize("/api/v1/transaction/{id}/reaction/summary", permitAll)
                authorize("/api/v1/transaction/{id}/comments", permitAll)
                authorize("/api/v1/tag/all", permitAll)
                authorize("/api/v1/tag/{id}", permitAll)
                authorize("/api/v1/character/catalog", permitAll)
                authorize("/api/v1/character/{memberId}", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/api/auth/**", permitAll)
                authorize("/login/**", permitAll)
                authorize("/oauth2/**", permitAll)
                authorize("/**", authenticated)
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
                allowedOriginPatterns = listOf(
                    "https://tiggle.duckdns.org",
                    "https://tiggle.duckdns.org:*",
                    "http://localhost:3000",
                    "http://localhost:5173"
                )
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
