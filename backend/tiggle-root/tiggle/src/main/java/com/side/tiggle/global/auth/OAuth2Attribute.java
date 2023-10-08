package com.side.tiggle.global.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
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
    private String providerId;

    static OAuth2Attribute of(String provider, String attributeKey,
                              Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao(attributeKey, attributes);
            case "naver":
                return ofNaver(attributeKey, attributes);
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
                .providerId((String)attributes.get("sub"))
                .attributes(attributes)
                .attributeKey((String)attributes.get(attributeKey))
                .build();
    }

    private static OAuth2Attribute ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuth2Attribute.builder()
                .nickname((String) response.get("name"))
                .email((String) response.get("email"))
                .profileUrl((String) response.get("profile_image"))
                .providerId((String) response.get("id"))
                .attributes(attributes)
                .attributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attribute ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .nickname((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileUrl((String) kakaoProfile.get("profile_image_url"))
                .providerId(attributes.get("id").toString())
                .attributes(attributes)
                .attributeKey(userNameAttributeName)
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