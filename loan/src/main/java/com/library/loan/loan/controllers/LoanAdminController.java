package com.library.loan.loan.controllers;

import com.library.loan.loan.LoanServiceImpl;
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
    public void deleteLoan(@PathVariable long id){
        loanService.deleteLoan(id);
    }

    @GetMapping("/{id}")
    public LoanResponse getLoanById(@PathVariable long id){
        return loanService.getLoanById(id);
    }

    @GetMapping("/")
    public List<LoanResponse> getAllLoans(){
        return loanService.getAllLoans();
    }

    @GetMapping("/not-returned")
    public List<LoanResponse> getNotReturnedLoans(){
        return loanService.getNotReturnedLoans();
    }

    @GetMapping("/user/{id}")
    public List<LoanResponse> getMemberLoans(@PathVariable long id){
        return loanService.getMemberLoans(id);
    }
}
