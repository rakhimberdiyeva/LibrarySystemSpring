package com.library.loan.loan;

import com.library.loan.loan.dto.*;
import com.library.loan.loan.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private NotificationPublisher notificationPublisher;

    @Override
    public LoanResponse borrowBook(long userId, LoanCreateRequest dto) {
        if (canBorrow(userId)){
            throw new BorrowLimitReached("borrow limit reached");
        }
        BookResponse book = catalogClient.getBook(dto.getBookId());
        if (book.getCopiesAvailable() <= 0){
            throw new BookNotAvailable("book is not available");
        }
        Loan loan = new Loan();
        loan.setMemberId(userId);
        loan.setBookId(dto.getBookId());
        loan.setBorrowDate(dto.getBorrowDate());
        catalogClient.reserveBook(book.getId());
        notificationPublisher.publish(new NotificationCreate(userId, NotificationType.BOOK_BORROWED, "book borrowed: " + loan.getBookId()));
        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanFinesResponse returnBook(long userId, long id, LoanUpdateRequest dto) {
        Loan loan = loanRepository.findById(id).orElseThrow(() ->  new LoanNotFound("loan not found: " + id));
        if (loan.getMemberId() != userId){
            throw new NotLoanOwner("users can update only their loan");
        }
        if (loan.getReturnDate() != null){
            throw new BookAlreadyReturned("book already returned error");
        }
        if (dto.getReturnDate() != null){
            loan.setReturnDate(dto.getReturnDate());
        }
        long fine = checkForFine(loan);
        catalogClient.releaseBook(loan.getBookId());
        loanRepository.save(loan);
        notificationPublisher.publish(new NotificationCreate(userId, NotificationType.BOOK_RETURNED, "book returned: " + loan.getBookId()));
        return new LoanFinesResponse(fine);
    }

    @Override
    public void deleteLoan(long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() ->  new LoanNotFound("loan not found: " + id));
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

    long checkForFine(Loan loan) {
        LocalDate dueDate = loan.getBorrowDate().plusDays(14);
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
            return Math.toIntExact(daysOverdue * 2);
        }
        return 0;
    }
}
