package com.library.auth.auth;

import com.library.auth.auth.dto.LoginMember;
import com.library.auth.auth.dto.RegisterMember;
import com.library.auth.auth.exceptions.EmailAlreadyExists;
import com.library.auth.auth.exceptions.UsernameAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String authenticateUser(LoginMember dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    public void registerUser(RegisterMember dto) {
        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExists("username already exists");
        }
        if (memberRepository.existsByEmail(dto.getUsername())) {
            throw new EmailAlreadyExists("email already exists");
        }
        Member member = new Member(
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                dto.getFullname(),
                dto.getEmail()
        );
        memberRepository.save(member);
    }
}
