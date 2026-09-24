package com.library.loan.loan;

import com.library.loan.loan.dto.LoanResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    LoanResponse toDto(Loan loan){
        LoanResponse dto = new LoanResponse();
        dto.setId(loan.getId());
        dto.setMemberId(loan.getMemberId());
        dto.setBookId(loan.getBookId());
        dto.setBorrowDate(loan.getBorrowDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setCreatedAt(loan.getCreatedAt());
        loan.setUpdatedAt(loan.getUpdatedAt());
        return dto;
    }
}
