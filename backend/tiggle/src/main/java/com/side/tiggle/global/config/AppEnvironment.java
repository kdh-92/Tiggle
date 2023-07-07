package com.side.tiggle.global.config;

import com.side.tiggle.domain.oauth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppEnvironment {

    public OAuthProvider google = new OAuthProvider();
}
