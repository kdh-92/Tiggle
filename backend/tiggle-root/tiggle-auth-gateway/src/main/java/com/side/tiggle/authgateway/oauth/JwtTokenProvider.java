package com.side.tiggle.authgateway.oauth;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final MemberRepository memberRepository;

    JwtTokenProvider(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    private String secret = "secrettigglesecrettigglesecrettiggle";
    private SecretKey secretKey;

    private final long tokenExpiry = 60L * 30L; // sec
    private final long refreshTokenExpiry = 60L * 60L * 24L; // sec

    @PostConstruct
    void init(){
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getAccessToken(long memberId, String roles) {
        return generateToken(memberId, roles, tokenExpiry);
    }

    public String getRefreshToken(long memberId, String roles) {
        return generateToken(memberId, roles, refreshTokenExpiry);
    }

    public String generateToken(long memberId, String roles, long validFor) {
        Date issued = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expiry = Date.from(LocalDateTime.now().plusSeconds(validFor).atZone(ZoneId.systemDefault()).toInstant());
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
        claims.put("roles", roles);

        return Jwts.builder()
                .setIssuer("tiggle")
                .setClaims(claims)
                .setIssuedAt(issued)
                .setExpiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        long memberId = this.getUserId(token);

        Optional<Member> member = this.memberRepository.findById(memberId);
        if (member.isEmpty()) {
            return null;
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new UsernamePasswordAuthenticationToken(MemberDto.fromEntity(member.get()), null, List.of(authority));
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring("Bearer ".length());
    }

    public String resolveRefreshToken(HttpServletRequest request){
        return request.getHeader("Refresh");
    }

    public long getUserId(String jwtToken){
        return Long.parseLong(extractClaims(jwtToken).getSubject());
    }

    public boolean isTokenValid(String token) {
        final Date expiry = extractClaims(token).getExpiration();
        return expiry.after(new Date());
    }

    private Claims extractClaims(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalStateException();
        }
    }

}
