package com.side.tiggle.authgateway.filter;

import com.side.tiggle.authgateway.oauth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            try {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new Exception("Token Not Provided");
                }

                String accessToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0)
                        .replace("Bearer ", "");
                if (!jwtTokenProvider.isTokenValid(accessToken)) {
                    throw new Exception("Invalid Token");
                }

                long memberId = jwtTokenProvider.getMemberId(accessToken);
                logger.info("Member authenticated: id {}", memberId);
                ServerHttpRequest mutatedRequest = request.mutate().header("x-member-id", Long.toString(memberId)).build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } catch (Exception e) {
                logger.info(e.getMessage());
                // 엑세스 토큰에 문제가 있다면 memberId 헤더를 지우고 그대로 relay 한다
                ServerHttpRequest mutatedRequest = request.mutate().header("x-member-id", "").build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            }
        });
    }

    public static class Config {

    }
}
