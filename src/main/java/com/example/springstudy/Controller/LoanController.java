package com.example.springstudy.Controller;

import com.example.springstudy.DTO.BookDTO;
import com.example.springstudy.DTO.LoanDTO;
import com.example.springstudy.Entity.LoanEntity;
import com.example.springstudy.Service.BookService;
import com.example.springstudy.Service.LoanService;
import com.example.springstudy.Service.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/loans")
public class LoanController {
  private LoanService loanService;
  private BookService bookService;
  private LoanDTO loan;


  @Autowired
  public LoanController(LoanService loanService, BookService bookService) {

    this.loanService = loanService;
    this.bookService=bookService;
  }


  @PostMapping("/create")
  public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
    try {
      LoanDTO createdLoan = loanService.createLoan(loanDTO);
      return ResponseEntity.ok(createdLoan);
    } catch (LoanServiceImpl.AlreadyLoanedException e) {
      return ResponseEntity.badRequest().body(new LoanDTO("이미 동일한 도서를 대출한 이력이 있습니다."));
    } catch (LoanServiceImpl.MaxLoansExceededException e) {
      return ResponseEntity.status(409).body(new LoanDTO("사용자가 대출 가능한 최대 권수(3권)를 초과하였습니다."));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new LoanDTO("서버 오류가 발생했습니다."));
    }
  }

  @GetMapping("/read")
  public List<LoanDTO> readAll() {

    return loanService.readAll();
  }


  //대출 사용자 조회
  @GetMapping("/users/{userId}/loans")
  public List<BookDTO> getLoansByUserId(@PathVariable(value = "userId") Long userId) {
    return loanService.getBooksByUserId(userId);
  }

  @PutMapping("/{id}/return")
  public ResponseEntity<String> returnBook(@PathVariable(value = "id") Long id) {
    try {
      // 책을 반납하고 결과를 반환
      String result = loanService.returnBook(id);

      if (result.equals("반납이 완료되었습니다.")) {
        return ResponseEntity.ok("책이 성공적으로 반납되었습니다.");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
      }
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("책 반납 중 오류가 발생했습니다.");
    }
  }




}
