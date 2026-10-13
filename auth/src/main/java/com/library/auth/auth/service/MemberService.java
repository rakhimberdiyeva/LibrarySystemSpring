package com.library.auth.auth.service;

import com.library.auth.auth.dto.*;
import com.library.auth.auth.model.RefreshToken;
import com.library.auth.auth.security.JwtUtil;
import com.library.auth.auth.exceptions.EmailAlreadyExists;
import com.library.auth.auth.exceptions.UsernameAlreadyExists;
import com.library.auth.auth.model.Member;
import com.library.auth.auth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RefreshTokenService refreshTokenService;

    public AuthResponse authenticateUser(LoginMember dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Member member = memberRepository.getReferenceById(user.getId());
        String accessToken = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getAuthorities());
        String refreshToken = refreshTokenService.create(member);
        return new AuthResponse(accessToken, refreshToken, user.getId(), user.getUsername());
    }

    public RegisterResponse registerUser(RegisterMember dto) {
        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExists("username already exists");
        }
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExists("email already exists");
        }
        Member member = new Member(
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                dto.getFullname(),
                dto.getEmail()
        );
        memberRepository.save(member);
        return new RegisterResponse(member.getUsername(), member.getEmail());
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest req) {
        RefreshToken old = refreshTokenService.consume(req.getRefreshToken());
        Member member = old.getMember();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        String accessToken = jwtUtils.generateToken(member.getId(), member.getUsername(), authorities);
        String refreshToken = refreshTokenService.create(member);
        return new AuthResponse(
                accessToken,
                refreshToken,
                member.getId(),
                member.getUsername());
    }
}
