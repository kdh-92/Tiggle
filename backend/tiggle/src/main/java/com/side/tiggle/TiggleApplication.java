package com.side.tiggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class TiggleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiggleApplication.class, args);
	}

}
