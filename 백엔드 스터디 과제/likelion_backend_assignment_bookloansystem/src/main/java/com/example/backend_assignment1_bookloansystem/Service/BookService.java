package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DTO.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    BookDTO addBook(BookDTO bookDTO);
    void deleteBook(Long id);
    BookDTO createBook(BookDTO bookDTO);  // createBook 메서드 추가
    // 필요한 다른 메서드들...
}

