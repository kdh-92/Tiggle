package com.side.tiggle.global.config

import com.side.tiggle.global.auth.JwtTokenProvider
import com.side.tiggle.global.auth.OAuth2SuccessHandler
import com.side.tiggle.global.filter.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean
    fun filterChain(http: HttpSecurity, successHandler: OAuth2SuccessHandler): SecurityFilterChain {
        http.httpBasic().disable()
            .cors().configurationSource(corsConfigurationSource()).and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it.requestMatchers(AntPathRequestMatcher("/**")).permitAll()
            }

        http.oauth2Login()
            .successHandler(successHandler)
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
