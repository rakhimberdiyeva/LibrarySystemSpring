package com.library.auth.controller;

import com.library.auth.auth.dto.AuthResponse;
import com.library.auth.auth.dto.LoginMember;
import com.library.auth.auth.dto.RefreshTokenRequest;
import com.library.auth.auth.dto.RegisterMember;
import com.library.auth.auth.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public AuthResponse authenticateUser(@Valid @RequestBody LoginMember dto) {
        return memberService.authenticateUser(dto);
    }
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterMember dto) {
        memberService.registerUser(dto);
        return "User registered successfully!";
    }
    @PostMapping("/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest dto) {
        return memberService.refresh(dto);
    }
}
