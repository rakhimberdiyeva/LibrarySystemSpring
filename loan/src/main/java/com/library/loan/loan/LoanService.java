package com.library.loan.loan;

import com.library.loan.loan.dto.LoanCreateRequest;
import com.library.loan.loan.dto.LoanResponse;
import com.library.loan.loan.dto.LoanUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {
    LoanResponse borrowBook(long userId, LoanCreateRequest dto);
    LoanResponse returnBook(long userId, long id, LoanUpdateRequest dto);
    void deleteLoan(long id);
    LoanResponse getLoanById(long id);
    List<LoanResponse> getAllLoans();
    List<LoanResponse> getNotReturnedLoans();
    List<LoanResponse> getMemberLoans(long memberId);
}
