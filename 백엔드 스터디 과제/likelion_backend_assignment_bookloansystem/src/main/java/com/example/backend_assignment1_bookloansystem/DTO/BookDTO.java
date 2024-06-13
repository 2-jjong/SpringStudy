package com.example.backend_assignment1_bookloansystem.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;

    // 모든 필드를 포함하는 생성자
    public BookDTO(Long id, String title, String author, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    // id, title, author만 포함하는 생성자
    public BookDTO(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

}
