package com.library.loan.loan.controllers;

import com.library.loan.loan.dto.*;
import com.library.loan.loan.services.LoanServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
public class LoanController {
    @Autowired
    private LoanServiceImpl loanService;

    @PostMapping("/")
    public ApiResponse<LoanResponse> borrow(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @RequestBody LoanCreateRequest dto){
        return new ApiResponse<>(200, "success", loanService.borrowBook(userId, dto));

    }

    @PutMapping("/{id}/return")
    public ApiResponse<LoanFinesResponse> returnBook(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @PathVariable long id, @RequestBody LoanUpdateRequest dto){
        return new ApiResponse<>(200, "success", loanService.returnBook(userId, id, dto));
    }

    @GetMapping("/my")
    public ApiResponse<List<LoanResponse>> getMemberLoans(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId){
        return new ApiResponse<>(200, "success",  loanService.getMemberLoans(userId));

    }
}
