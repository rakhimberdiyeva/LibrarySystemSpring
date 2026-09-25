package com.library.loan.loan.controllers;

import com.library.loan.loan.LoanServiceImpl;
import com.library.loan.loan.dto.LoanCreateRequest;
import com.library.loan.loan.dto.LoanResponse;
import com.library.loan.loan.dto.LoanUpdateRequest;
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
    public LoanResponse borrow(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @RequestBody LoanCreateRequest dto){
        return loanService.borrowBook(userId, dto);
    }

    @PutMapping("/{id}/return")
    public LoanResponse returnBook(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @PathVariable long id, @RequestBody LoanUpdateRequest dto){
        return loanService.returnBook(userId, id, dto);
    }

    @GetMapping("/my")
    public List<LoanResponse> getMemberLoans(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId){
        return loanService.getMemberLoans(userId);
    }
}
