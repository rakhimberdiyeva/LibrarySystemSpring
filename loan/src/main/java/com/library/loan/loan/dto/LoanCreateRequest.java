package com.library.loan.loan.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanCreateRequest {
    private long memberId;
    private long bookId;
    private LocalDate borrowDate;
}
