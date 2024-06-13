package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DAO.BookDAO;
import com.example.backend_assignment1_bookloansystem.DTO.BookDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookDAO.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookDAO.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookDAO.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookDAO.save(book);
        return convertToDTO(savedBook);
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPublishedDate());
    }

    private Book convertToEntity(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPublishedDate());
    }
}

