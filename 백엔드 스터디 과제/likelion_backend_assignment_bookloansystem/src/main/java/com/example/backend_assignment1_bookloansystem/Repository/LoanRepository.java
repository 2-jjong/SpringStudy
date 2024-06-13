package com.example.backend_assignment1_bookloansystem.Repository;

import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    // 특정 사용자 ID로 대출된 책 목록을 찾는 메서드
    List<Loan> findByUserId(Long userId);

}

