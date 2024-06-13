package com.example.backend_assignment1_bookloansystem.Repository;

import com.example.backend_assignment1_bookloansystem.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// BookRepository.java
public interface BookRepository extends JpaRepository<Book, Long> {
}
