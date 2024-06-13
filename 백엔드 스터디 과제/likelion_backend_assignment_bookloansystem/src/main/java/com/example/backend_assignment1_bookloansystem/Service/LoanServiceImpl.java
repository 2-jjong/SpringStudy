package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DAO.BookDAO;
import com.example.backend_assignment1_bookloansystem.DAO.LoanDAO;
import com.example.backend_assignment1_bookloansystem.DAO.UserDAO;
import com.example.backend_assignment1_bookloansystem.DTO.BookDTO;
import com.example.backend_assignment1_bookloansystem.DTO.LoanDTO;
import com.example.backend_assignment1_bookloansystem.DTO.UserDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Book;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanDAO loanDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final BookService bookService; // 추가
    private final UserService userService; // 추가

    @Autowired
    public LoanServiceImpl(LoanDAO loanDAO, UserDAO userDAO, BookDAO bookDAO, BookService bookService, UserService userService) {
        this.loanDAO = loanDAO;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.bookService = bookService; // 추가
        this.userService = userService; // 추가
    }

    @Override
    public LoanDTO createLoan(Long bookId, Long userId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        UserDTO userDTO = userService.getUserById(userId);

        if (bookDTO == null || userDTO == null) {
            throw new IllegalArgumentException("Invalid book or user ID");
        }

        Loan loan = new Loan();
        loan.setBook(bookDAO.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found")));
        loan.setUser(userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        loan.setLoanDate(LocalDate.now());

        Loan savedLoan = loanDAO.save(loan);
        return convertToDTO(savedLoan);
    }

    @Override
    public LoanDTO getLoanById(Long id) {
        Optional<Loan> loan = loanDAO.findById(id);
        return loan.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = loanDAO.findAll();
        return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteLoanById(Long id) {
        loanDAO.deleteById(id);
    }

    @Override
    public LoanDTO addLoan(Long userId, Long bookId) {
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookDAO.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(null);  // 대출 시 반환일자는 설정하지 않음

        Loan savedLoan = loanDAO.save(loan);

        return convertToDTO(savedLoan);
    }

    @Override
    public Loan returnBook(Long id) {
        Loan loan = loanDAO.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setReturnDate(LocalDate.now());
        return loanDAO.save(loan);
    }

    private LoanDTO convertToDTO(Loan loan) {
        return new LoanDTO(loan.getId(), loan.getUser().getId(), loan.getBook().getId(), loan.getLoanDate(), loan.getReturnDate());
    }
}
