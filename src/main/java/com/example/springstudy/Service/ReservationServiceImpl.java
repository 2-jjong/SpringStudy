package com.example.springstudy.Service;

import com.example.springstudy.Entity.BookEntity;
import com.example.springstudy.Entity.ReservationEntity;
import com.example.springstudy.Entity.UserEntity;
import com.example.springstudy.Repository.BookRepository;
import com.example.springstudy.Repository.ReservationRepository;
import com.example.springstudy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

  private ReservationRepository reservationRepository;
  private UserRepository userRepository;

  private BookRepository bookRepository;
  private BookEntity book;

  @Autowired
  public void LoanServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
    this.reservationRepository = reservationRepository;
    this.userRepository=userRepository;
    this.bookRepository=bookRepository;
  }

  @Override
  public String createReservation(Long bookId, Long userId) {
    // 책과 사용자를 ID로 조회
    Optional<BookEntity> bookOpt = bookRepository.findById(bookId);
    Optional<UserEntity> userOpt = userRepository.findById(userId);

    // 책이나 사용자가 존재하지 않을 경우
    if (!bookOpt.isPresent()) {
      return "책을 찾을 수 없습니다.";
    }
    if (!userOpt.isPresent()) {
      return "사용자를 찾을 수 없습니다.";
    }

    BookEntity book = bookOpt.get();
    UserEntity user = userOpt.get();

    // 책이 대여 가능한지 확인
    if (!book.isAvailable()) {
      return "책이 대여 가능하지 않습니다.";
    }

    // 예약 생성
    ReservationEntity reservation = ReservationEntity.builder()
        .book(book)
        .userEntity(user)
        .build();
    reservationRepository.save(reservation);

    // 책의 상태 업데이트: 대여 상태로 변경
    book.setAvailable(false);
    bookRepository.save(book);

    return "예약이 성공적으로 생성되었습니다.";
  }





  public List<ReservationEntity> getReservationsByBook(Long bookId) {
    return reservationRepository.findByBookId(bookId);
  }


  public void deleteReservation(ReservationEntity reservation) {
    reservationRepository.delete(reservation);
  }

  @Override
  public ReservationEntity getNextReservation(Long bookId) {
    // bookId를 사용하여 BookEntity를 조회합니다.
    BookEntity book = bookRepository.findById(bookId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + bookId));

    // 조회한 BookEntity를 사용하여 예약 정보를 조회합니다.
    return reservationRepository.findFirstByBookOrderByReservationDateAsc(book)
        .orElse(null);
  }
}
