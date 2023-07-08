package com.side.tiggle.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Optional<Member> member = this.memberRepository.findByEmail(oAuth2User.getAttribute("email"));
        Member authMember;
        log.info("oAuth2User : {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        if (member.isEmpty()) {
            authMember = new Member(
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("profile_url"),
                    oAuth2User.getAttribute("nickname")
            );
            this.memberRepository.save(authMember);
        }
        else {
            authMember = member.get();
        }

        // 토큰 발급
        String token = jwtTokenProvider.getAccessToken(authMember.getId(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.getRefreshToken(authMember.getId(), "ROLE_USER");
        String targetUrl = UriComponentsBuilder.fromUriString("/api/external")
                .queryParam("token", token)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
