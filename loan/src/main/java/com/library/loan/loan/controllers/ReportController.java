package com.library.loan.loan.controllers;

import com.library.loan.loan.dto.ApiResponse;
import com.library.loan.loan.dto.OverdueLoanResponse;
import com.library.loan.loan.mappers.LoanMapper;
import com.library.loan.loan.services.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/report/")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private LoanMapper loanMapper;

    @GetMapping("/all")
    public ApiResponse<List<OverdueLoanResponse>> getOverdueLoans(){
        List<OverdueLoanResponse> loans = reportService.getOverdueLoans().entrySet().stream()
                .map(e -> new OverdueLoanResponse(loanMapper.toDto(e.getKey()), e.getValue()))
                .toList();
        return new ApiResponse<>(200, "success", loans);
    }

    @GetMapping("/total")
    public ApiResponse<Long> totalFines(){
        return new ApiResponse<>(200, "sucess", reportService.totalFines());
    }

    @GetMapping("/most-fines")
    public ApiResponse<Optional<Long>> mostFines(){
        return new ApiResponse<>(200, "success", reportService.mostFines());
    }

    @GetMapping("/most-borrowed")
    public ApiResponse<Optional<Long>>mostBorrowed(){
        return new ApiResponse<>(200, "success", reportService.mostBorrowed());
    }
}
