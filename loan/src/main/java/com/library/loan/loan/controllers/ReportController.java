package com.library.loan.loan.controllers;

import com.library.loan.loan.Loan;
import com.library.loan.loan.LoanMapper;
import com.library.loan.loan.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/report/")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private LoanMapper loanMapper;

    @GetMapping("/all")
    public Map<Loan, Long> getOverdueLoans(){
        Map<Loan, Long> loans = reportService.getOverdueLoans();
        return loans;
    }

    @GetMapping("/total")
    public long totalFines(){
        return reportService.totalFines();
    }

    @GetMapping("/most-fines")
    public Optional<Long> mostFines(){
        return reportService.mostFines();
    }

    @GetMapping("/most-borrowed")
    public Optional<Long> mostBorrowed(){
        return reportService.mostBorrowed();
    }
}
