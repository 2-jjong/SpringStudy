package com.example.backend_assignment1_bookloansystem.DAO;

import com.example.backend_assignment1_bookloansystem.Entity.Book;
import com.example.backend_assignment1_bookloansystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAO {

    private final BookRepository bookRepository;

    @Autowired
    public BookDAO(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}

