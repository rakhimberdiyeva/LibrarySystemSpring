package com.library.loan.loan.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoanResponse {
    private long id;
    private long memberId;
    private long bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
