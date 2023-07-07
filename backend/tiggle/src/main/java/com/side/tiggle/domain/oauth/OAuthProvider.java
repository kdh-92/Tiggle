package com.side.tiggle.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OAuthProvider {
    private String clientId;
    private String clientSecret;
    private String tokenUri;
    private String resourceUri;
    private String redirectUri;
}