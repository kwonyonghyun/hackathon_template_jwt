package com.example.hackathon_template_jwt.config.jwt;

import com.example.hackathon_template_jwt.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user, Duration expiredAt) {
        return generateToken(user, expiredAt, "ACCESS");
    }

    public String generateRefreshToken(User user, Duration expiredAt) {
        return generateToken(user, expiredAt, "REFRESH");
    }

    private String generateToken(User user, Duration expiredAt, String tokenType) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("tokenType", tokenType)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }


    public boolean validAccessToken(String token) {
        return validToken(token, "ACCESS");
    }

    public boolean validRefreshToken(String token) {
        return validToken(token, "REFRESH");
    }

    private boolean validToken(String token, String expectedTokenType) {
        try {
            Claims claims = getClaims(token);
            return expectedTokenType.equals(claims.get("tokenType"));
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject
                (), "", authorities), token, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
