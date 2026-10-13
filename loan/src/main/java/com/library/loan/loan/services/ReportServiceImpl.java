package com.library.loan.loan.services;

import com.library.loan.loan.models.Loan;
import com.library.loan.loan.repository.LoanRepository;
import com.library.loan.loan.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private LoanRepository loanRepository;

    public Map<Loan, Long> getOverdueLoans(){
        List<Loan> loans = loanRepository.findByReturnDateIsNull();
        Map<Loan, Long> fines = new HashMap<>();
        for (Loan loan : loans){
            LocalDate dueDate = loan.getBorrowDate().plusDays(14);
            LocalDate today = LocalDate.now();
            if (today.isAfter(dueDate)){
                long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                fines.put(loan, daysOverdue);
            }
        }
        return fines;
    }

    public long totalFines(){
        Map<Loan, Long> loans = getOverdueLoans();
        Collection<Long> fines = loans.values();
        long total = fines.stream().reduce(0L, (n1, n2) -> n1 + n2);
        return total;
    }

    public Optional<Long> mostFines() {
        Map<Loan, Long> loans = getOverdueLoans();

        Map<Long, Long> finesByMember = loans.entrySet().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getKey().getMemberId(),
                        Collectors.summingLong(e -> e.getValue())
                ));

        return finesByMember.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public Optional<Long> mostBorrowed() {
        List<Loan> loans = loanRepository.findAll();

        Map<Long, Long> borrowedPerBook = loans.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getBookId(),
                        Collectors.counting()
                ));

        return borrowedPerBook.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
