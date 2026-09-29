package com.library.loan.loan;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface ReportService {
    Map<Loan, Long>  getOverdueLoans();
    long totalFines();
    Optional<Long> mostFines();
}
