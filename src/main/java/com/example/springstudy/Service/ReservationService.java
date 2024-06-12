package com.example.springstudy.Service;

import com.example.springstudy.Entity.ReservationEntity;

import java.util.List;

public interface ReservationService {
  String createReservation(Long bookId, Long userId);

  List<ReservationEntity> getReservationsByBook(Long bookId);

  ReservationEntity getNextReservation(Long bookId);

  void deleteReservation(ReservationEntity nextReservation);
}
