package com.library.auth.auth;

import com.library.auth.auth.dto.LoginMember;
import com.library.auth.auth.dto.RegisterMember;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public String authenticateUser(@Valid @RequestBody LoginMember dto) {
        return memberService.authenticateUser(dto);
    }
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterMember dto) {
        memberService.registerUser(dto);
        return "User registered successfully!";
    }
}
