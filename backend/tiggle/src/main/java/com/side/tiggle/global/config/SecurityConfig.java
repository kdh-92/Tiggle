package com.side.tiggle.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable().cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/**").permitAll() // 인증은 누구나 접근 가능
                .antMatchers("/api/**").hasRole("USER"); // User만 API 사용 가능
        return http.build();
    }
}
