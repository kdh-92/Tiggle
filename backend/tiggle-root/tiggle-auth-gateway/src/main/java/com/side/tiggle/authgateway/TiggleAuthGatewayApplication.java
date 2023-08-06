package com.side.tiggle.authgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.side")
public class TiggleAuthGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiggleAuthGatewayApplication.class, args);
    }

}
