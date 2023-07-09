package com.side.tiggle.global.config;

import com.side.tiggle.global.auth.OAuth2FailureHandler;
import com.side.tiggle.global.auth.OAuth2Service;
import com.side.tiggle.global.auth.OAuth2SuccessHandler;
import com.side.tiggle.global.auth.JwtTokenProvider;
import com.side.tiggle.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final OAuth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/external/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and()
                .logout().logoutSuccessUrl("/logout") // 로그아웃 리다이렉트 위치
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .failureHandler(oAuth2FailureHandler)
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint().userService(oAuth2Service);
        return http.build();
    }
}
