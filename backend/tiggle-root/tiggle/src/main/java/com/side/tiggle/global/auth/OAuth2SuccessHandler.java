package com.side.tiggle.global.auth;

import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.client-redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        Member authMember = getAndUpsert(authenticationToken);

        // 토큰 발급
        String token = jwtTokenProvider.getAccessToken(authMember.getId(), "ROLE_USER");

        // 토큰을 cookie에 담아서 보낸다
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setMaxAge(60000);
        cookie.setPath("/"); // FIXME: 특정 경로에서만 사용 가능하도록 수정한다
        response.addCookie(cookie);
        response.sendRedirect(redirectUri);
    }

    private Member getAndUpsert(OAuth2AuthenticationToken authenticationToken) {
        DefaultOAuth2User userPrincipal = (DefaultOAuth2User) authenticationToken.getPrincipal();
        log.info("oAuth2User : {}", userPrincipal);

        OAuth2Attribute attributes = OAuth2Attribute.of(authenticationToken.getAuthorizedClientRegistrationId(), userPrincipal.getName(), userPrincipal.getAttributes());
        Member member = this.memberRepository.findByEmail(attributes.getEmail());
        attributes.getAttributes().get("id");
        Member authMember = getMember(authenticationToken, member, attributes);
        this.memberRepository.save(authMember);

        return authMember;
    }

    @NotNull
    private static Member getMember(OAuth2AuthenticationToken authenticationToken, Member member, OAuth2Attribute attributes) {
        Member authMember;
        // 최초 로그인이라면 회원가입 처리를 한다.
        if (member == null) {
            authMember = new Member(
                    attributes.getEmail(),
                    attributes.getProfileUrl(),
                    attributes.getNickname(),
                    null,
                    authenticationToken.getAuthorizedClientRegistrationId(),
                    attributes.getProviderId()
            );
        }
        else {
            // 이미 가입 되어있다면 수정한다
            authMember = member;
            authMember.setProfileUrl(attributes.getProfileUrl());
            authMember.setProvider(authenticationToken.getAuthorizedClientRegistrationId());
        }
        return authMember;
    }

}