package com.example.springstudy.DTO;

import com.example.springstudy.Entity.BookEntity;
import com.example.springstudy.Entity.UserEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReservationDTO {
  private Long userId;
  private Long bookId;



  public ReservationDTO(Long userId, Long bookId) {
    this.userId = userId;
    this.bookId = bookId;
  }


  // Getters and Setters
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }
}
