package com.example.backend_assignment1_bookloansystem.Controller;

import com.example.backend_assignment1_bookloansystem.DTO.LoanDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> addLoan(@RequestParam Long userId, @RequestParam Long bookId) {
        LoanDTO createdLoan = loanService.addLoan(userId, bookId);
        return ResponseEntity.ok(createdLoan);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        Loan returnedLoan = loanService.returnBook(id);
        return ResponseEntity.ok(returnedLoan);
    }
}

