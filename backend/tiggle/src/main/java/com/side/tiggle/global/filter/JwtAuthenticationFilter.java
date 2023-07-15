package com.side.tiggle.global.filter;

import com.side.tiggle.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final String[] excludePatterns = {
//            "/api/v1",
//            "/login",
            "/"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = jwtTokenProvider.resolveAccessToken(request);
            
            // 엑세스 토큰 검증
            if (accessToken == null) {
                throw new IllegalStateException("No Access Token");
            }
            if (!jwtTokenProvider.isTokenValid(accessToken)){
                throw new IllegalStateException("Access Token Expired");
            }
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (IllegalStateException e) {
            // TODO 에러를 세분화 해야 함
            log.error("[{}] {}", this.getClass().getSimpleName(), e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(excludePatterns).anyMatch(path::startsWith);
    }
}
