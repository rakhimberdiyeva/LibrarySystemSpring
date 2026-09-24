package com.library.loan.loan;

import com.library.loan.loan.dto.LoanCreateRequest;
import com.library.loan.loan.dto.LoanResponse;
import com.library.loan.loan.dto.LoanUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loan")
public class LoanController {
    @Autowired
    private LoanServiceImpl loanService;

    @PostMapping("/")
    public LoanResponse createLoan(@RequestBody LoanCreateRequest dto){
        return loanService.createLoan(dto);
    }

    @PutMapping("/{id}/return")
    public LoanResponse updateReturnDate(@PathVariable long id, @RequestBody LoanUpdateRequest dto){
        return loanService.updateReturnDate(id, dto);
    }

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
