package com.side.tiggle.authgateway.oauth;

import com.side.tiggle.authgateway.member.Member;
import com.side.tiggle.authgateway.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements ServerAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Optional<Member> member = this.memberRepository.findByEmail(oAuth2User.getAttribute("email"));
        Member authMember;
        logger.info("oAuth2User : {}", oAuth2User);

        if (member.isEmpty()) {
            authMember = new Member(
                oAuth2User.getAttribute("email"),
                oAuth2User.getAttribute("picture"),
                oAuth2User.getAttribute("name"),
                LocalDate.of(1990, 1, 1)
            );
            this.memberRepository.save(authMember);
            logger.info("Added new user : {}", authMember);
        }
        else {
            authMember = member.get();
        }

        String token = jwtTokenProvider.getAccessToken(authMember.getId(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.getRefreshToken(authMember.getId(), "ROLE_USER");
        String targetUrl = UriComponentsBuilder.fromUriString("/login/success")
                .build().toUriString();

        ServerHttpResponse ex = webFilterExchange.getExchange().getResponse();
        ex.getHeaders().add(HttpHeaders.LOCATION, targetUrl);
        ex.getHeaders().add("Authorization", "Bearer " + token);
        ex.getHeaders().add("Refresh", refreshToken);
        webFilterExchange.getExchange().mutate().response(ex);
        return Mono.empty();
    }
}
