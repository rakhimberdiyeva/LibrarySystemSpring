package com.library.auth.auth.security;

import com.library.auth.auth.exceptions.InvalidTokenError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SecurityException e) {
            throw new InvalidTokenError("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new InvalidTokenError("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenError("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenError("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenError("JWT claims string is empty: " + e.getMessage());
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public List<GrantedAuthority> getAuthorities(Claims claims) {
        List<?> roles = claims.get("roles", List.class);
        if (roles == null) return List.of();
        return roles.stream()
                .map(r -> (GrantedAuthority) new SimpleGrantedAuthority(r.toString()))
                .toList();
    }
}