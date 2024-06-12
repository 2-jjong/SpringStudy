package com.example.springstudy.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReservationEntity {
  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  private BookEntity book;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;

  @Column(name = "reservation_date", nullable = false)
  private Date reservationDate;

  @Builder
  public ReservationEntity(BookEntity book, UserEntity userEntity, Date reservationDate) {
    this.book = book;
    this.userEntity = userEntity;
    this.reservationDate = reservationDate != null ? reservationDate : new Date();
  }

  public UserEntity getUser() {
    return userEntity;
  }
  public boolean isAvailable() {
    return book.isAvailable();
  }
}
