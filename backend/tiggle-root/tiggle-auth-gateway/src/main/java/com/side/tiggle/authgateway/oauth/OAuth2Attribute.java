package com.side.tiggle.authgateway.oauth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String profileUrl;
    private String birthString;
    private String nickname;

    static OAuth2Attribute of(String provider, String attributeKey,
                              Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                throw new IllegalStateException();
            case "naver":
                throw new IllegalStateException();
            default:
                throw new IllegalStateException();
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {

        return OAuth2Attribute.builder()
                .email((String) attributes.get("email"))
                .profileUrl((String)attributes.get("picture"))
                .nickname((String)attributes.get("name"))
                .attributes(attributes)
                .attributeKey((String)attributes.get(attributeKey))
                .build();
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("profile_url", profileUrl);
        map.put("nickname", nickname);

        return map;
    }
}