package com.library.loan.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OverdueLoanResponse {
    private LoanResponse loan;
    private long daysOverdue;
}
