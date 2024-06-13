package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DTO.LoanDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Book;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Entity.User;
import com.example.backend_assignment1_bookloansystem.Repository.BookRepository;
import com.example.backend_assignment1_bookloansystem.Repository.LoanRepository;
import com.example.backend_assignment1_bookloansystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoanService {
    LoanDTO createLoan(Long bookId, Long userId);
    LoanDTO getLoanById(Long id);
    List<LoanDTO> getAllLoans();
    void deleteLoanById(Long id);
    LoanDTO addLoan(Long userId, Long bookId);
    Loan returnBook(Long id);
}
