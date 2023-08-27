package com.side.tiggle.authgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // exception 발생한 경우 BAD_REQUEST 처리하고 response를 에러 메세지로 덮어씌운다.
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        logger.info(ex.getMessage());
        byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
