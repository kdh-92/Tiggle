package com.side.tiggle.authgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 모든 Gateway 요청을 로깅하는 필터
 */
@Component
public class LogFilter implements GlobalFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // TODO: 로깅 개선
        logger.info("{} {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath().value());
        return chain.filter(exchange);
    }
}
