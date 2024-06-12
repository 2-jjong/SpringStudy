package com.example.springstudy.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String title;
  @Column
  private String author;
  @Column
  private Long isbn;
  @Column
  private String publishedDate;
  private boolean isBorrowed;
  private int currentLoans = 0;  // 현재 대출 중인 사람 수 초기화
  private static final int MAX_LOANS = 3;
  private boolean available = true; // 기본값을 true로 설정


  @Builder
  public BookEntity(String title, String author, Long isbn, String publishedDate) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.publishedDate = publishedDate;
  }

  public BookEntity(Long bookId) {
  }

  public void updateTitle(String title) {

    this.title = title;
  }

  public void updateAuthor(String author) {

    this.author = author;
  }

  public void updateIsbn(Long isbn) {

    this.isbn = isbn;
  }


  public String getPublishedDate() {

    return publishedDate;
  }


  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public boolean isBorrowed() {

    return this.isBorrowed;
  }

  public int getCurrentLoans() {
    return currentLoans;
  }

  public void setCurrentLoans(int currentLoans) {
    this.currentLoans = currentLoans;
  }

  public int getMaxLoans() {
    return MAX_LOANS;
  }


  public boolean isAvailable() {

    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public boolean isAvailableForReturn() {
    // 책이 현재 대출 중이라면 반납할 수 없음
    return !this.available; // available이 대출 중을 나타내는 상태라고 가정
  }

}



