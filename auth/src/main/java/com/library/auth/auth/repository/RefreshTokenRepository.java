package com.library.auth.auth.repository;

import com.library.auth.auth.model.Member;
import com.library.auth.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
     void deleteByMember(Member member);
     Optional<RefreshToken> findByTokenHash(String tokenHash);
}

