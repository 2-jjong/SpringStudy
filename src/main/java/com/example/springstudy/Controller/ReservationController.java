package com.example.springstudy.Controller;

import com.example.springstudy.DTO.ReservationDTO;
import com.example.springstudy.Entity.ReservationEntity;
import com.example.springstudy.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
  @Autowired
  private ReservationService reservationService;


  @PostMapping("/create/{bookId}/{userId}")
  public ResponseEntity<String> createReservation(@PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) {
    try {
      String result = reservationService.createReservation(bookId, userId);
      if (result.equals("예약이 성공적으로 생성되었습니다.")) {
        return ResponseEntity.ok(result);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
  }
  @GetMapping("/book/{bookId}")
  public ResponseEntity<List<ReservationEntity>> getReservationsByBook(@PathVariable Long bookId) {
    List<ReservationEntity> reservations = reservationService.getReservationsByBook(bookId);
    return ResponseEntity.ok(reservations);
  }
}
