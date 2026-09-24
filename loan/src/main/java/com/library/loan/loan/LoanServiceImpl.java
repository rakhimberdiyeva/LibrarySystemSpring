package com.library.loan.loan;

import com.library.loan.loan.dto.LoanCreateRequest;
import com.library.loan.loan.dto.LoanResponse;
import com.library.loan.loan.dto.LoanUpdateRequest;
import com.library.loan.loan.exceptions.BookAlreadyReturned;
import com.library.loan.loan.exceptions.BorrowLimitReached;
import com.library.loan.loan.exceptions.LoanNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanMapper loanMapper;

    @Override
    public LoanResponse createLoan(LoanCreateRequest dto) {
        // TODO: check book id
        if (canBorrow(dto.getMemberId())){
            throw new BorrowLimitReached("borrow limit reached");
        }
        Loan loan = new Loan();
        loan.setMemberId(dto.getMemberId());
        loan.setBookId(dto.getBookId());
        loan.setBorrowDate(dto.getBorrowDate());
        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanResponse updateReturnDate(long id, LoanUpdateRequest dto) {
        // TODO: check for owner
        Loan loan = loanRepository.findById(id).orElseThrow(() ->  new LoanNotFound("loan not found: " + id));
        if (loan.getReturnDate() != null){
            throw new BookAlreadyReturned("book already returned error");
        }

        if (dto.getReturnDate() != null){
            loan.setReturnDate(dto.getReturnDate());
        }
        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public void deleteLoan(long id) {
        // TODO: check for admin
        Loan loan = loanRepository.findById(id).orElseThrow(() ->  new LoanNotFound("loan not found: " + id));
        if (loan.getReturnDate() != null){
            throw new BookAlreadyReturned("book already returned error");
        }
        loanRepository.delete(loan);
    }

    @Override
    public LoanResponse getLoanById(long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() ->  new LoanNotFound("loan not found: " + id));
        return loanMapper.toDto(loan);
    }

    @Override
    public List<LoanResponse> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(l -> loanMapper.toDto(l)).toList();
    }

    @Override
    // TODO: check for admin
    public List<LoanResponse> getNotReturnedLoans() {
        List<Loan> loans = loanRepository.findByReturnDateIsNull();
        return loans.stream().map(l -> loanMapper.toDto(l)).toList();
    }

    @Override
    public List<LoanResponse> getMemberLoans(long memberId) {
        List<Loan> loans = loanRepository.findByMemberId(memberId);
        return loans.stream().map(l -> loanMapper.toDto(l)).toList();
    }

    private boolean canBorrow(long memberId) {
        List<Loan> loans = loanRepository.findByMemberIdAndReturnDateIsNull(memberId);
        return loans.size() >= 3;
    }
}
