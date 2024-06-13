package com.example.backend_assignment1_bookloansystem.DAO;

import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LoanDAO {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanDAO(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public List<Loan> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }
}

