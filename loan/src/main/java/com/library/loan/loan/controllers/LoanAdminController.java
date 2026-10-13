package com.library.loan.loan.controllers;

import com.library.loan.loan.dto.ApiResponse;
import com.library.loan.loan.services.LoanServiceImpl;
import com.library.loan.loan.dto.LoanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/loans")
public class LoanAdminController {
    @Autowired
    private LoanServiceImpl loanService;

    @DeleteMapping("/{id}")
    public ApiResponse deleteLoan(@PathVariable long id){
        loanService.deleteLoan(id);
        return new ApiResponse<>(204, "success", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanResponse> getLoanById(@PathVariable long id){
        return new ApiResponse<>(200, "success", loanService.getLoanById(id));
    }

    @GetMapping({"", "/"})
    public ApiResponse<List<LoanResponse>> getAllLoans(){
        return new ApiResponse<>(200, "success", loanService.getAllLoans());
    }

    @GetMapping("/not-returned")
    public ApiResponse<List<LoanResponse>> getNotReturnedLoans(){
        return new ApiResponse<>(200, "success", loanService.getNotReturnedLoans());
    }

    @GetMapping("/user/{id}")
    public ApiResponse<List<LoanResponse>> getMemberLoans(@PathVariable long id){
        return new ApiResponse<>(200, "success", loanService.getMemberLoans(id));
    }
}
