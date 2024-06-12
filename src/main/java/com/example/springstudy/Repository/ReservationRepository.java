package com.example.springstudy.Repository;

import com.example.springstudy.Entity.BookEntity;
import com.example.springstudy.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
  Optional<ReservationEntity> findFirstByBookOrderByReservationDateAsc(BookEntity book);

  List<ReservationEntity> findByBookId(Long bookId);
}