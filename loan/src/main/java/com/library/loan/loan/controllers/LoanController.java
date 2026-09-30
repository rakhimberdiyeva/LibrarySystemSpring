package com.library.loan.loan.controllers;

import com.library.loan.loan.dto.*;
import com.library.loan.loan.services.LoanServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/loans")
public class LoanController {
    @Autowired
    private LoanServiceImpl loanService;

    @PostMapping("/")
    public ApiResponse<LoanResponse> borrow(@AuthenticationPrincipal Jwt jwt, @RequestBody LoanCreateRequest dto){
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        return new ApiResponse<>(200, "success", loanService.borrowBook(userId, dto));
    }

    @PutMapping("/{id}/return")
    public ApiResponse<LoanFinesResponse> returnBook(@AuthenticationPrincipal Jwt jwt, @PathVariable long id, @RequestBody LoanUpdateRequest dto){
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        return new ApiResponse<>(200, "success", loanService.returnBook(userId, id, dto));
    }

    @GetMapping("/my")
    public ApiResponse<List<LoanResponse>> getMemberLoans(@AuthenticationPrincipal Jwt jwt){
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        return new ApiResponse<>(200, "success",  loanService.getMemberLoans(userId));
    }
}
