package com.side.tiggle.domain.oauth.api;

import com.side.tiggle.domain.oauth.google.GoogleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/auth/oauth2")
public class LoginController {

    GoogleService googleService;

    @GetMapping("/google")
    public void googleLogin(@RequestParam String authCode) {
        googleService.login(authCode);
    }

}
