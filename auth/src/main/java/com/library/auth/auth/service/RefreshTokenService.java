package com.library.auth.auth.service;

import com.library.auth.auth.exceptions.InvalidTokenError;
import com.library.auth.auth.model.Member;
import com.library.auth.auth.model.RefreshToken;
import com.library.auth.auth.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationMs;

    private final SecureRandom random = new SecureRandom();

    public String create(Member member) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        RefreshToken rt = new RefreshToken();
        rt.setTokenHash(hash(raw));
        rt.setMember(member);
        rt.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        refreshTokenRepository.save(rt);

        return raw;
    }

    @Transactional
    public RefreshToken consume(String raw) {
        RefreshToken rt = refreshTokenRepository.findByTokenHash(hash(raw))
                .orElseThrow(() -> new InvalidTokenError("Invalid refresh token"));
        refreshTokenRepository.delete(rt);
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidTokenError("Refresh token expired");
        }
        return rt;
    }

    public void revokeAll(Member member) {
        refreshTokenRepository.deleteByMember(member);
    }

    private String hash(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(raw.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
