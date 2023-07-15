package com.side.tiggle.global.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "테스트 목적 컨트롤러")
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthTokenController {

    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "토큰 발급 API")
    @GetMapping("/token/{id}")
    String getAccessToken(@PathVariable Long id) {
        return jwtTokenProvider.getAccessToken(id, "ROLE_USER");
    }

}
