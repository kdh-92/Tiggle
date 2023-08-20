package com.side.tiggle.authgateway.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    
    // TODO: application.yml에서 환경에 따라 가져오도록 설정해야 함
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

    public String resolveAccessToken(ServerHttpRequest request) {
        return request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring("Bearer ".length());
    }

    public String resolveRefreshToken(ServerHttpRequest request){
        return request.getHeaders().get("Refresh").get(0);
    }

    public long getMemberId(String jwtToken){
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
