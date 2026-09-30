package com.library.auth.auth.controller;

import com.library.auth.auth.dto.*;
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
    public ApiResponse<AuthResponse> authenticateUser(@Valid @RequestBody LoginMember dto) {
        return new ApiResponse<>(200, "success", memberService.authenticateUser(dto));
    }
    @PostMapping("/register")
    public ApiResponse<RegisterResponse> registerUser(@Valid @RequestBody RegisterMember dto) {
        return new ApiResponse<>(201, "success", memberService.registerUser(dto));
    }
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest dto) {
        return new ApiResponse<>(200, "success", memberService.refresh(dto));
    }
}
