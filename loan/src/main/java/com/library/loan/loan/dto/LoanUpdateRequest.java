package com.library.loan.loan.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanUpdateRequest {
    private LocalDate returnDate;
}
