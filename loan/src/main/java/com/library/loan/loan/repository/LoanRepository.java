package com.library.loan.loan.repository;

import com.library.loan.loan.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByMemberId(long memberId);
    List<Loan> findByMemberIdAndReturnDateIsNull(long memberId);
}
