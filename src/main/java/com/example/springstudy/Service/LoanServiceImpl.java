package com.example.springstudy.Service;

import com.example.springstudy.DTO.BookDTO;
import com.example.springstudy.DTO.LoanDTO;
import com.example.springstudy.Entity.BookEntity;
import com.example.springstudy.Entity.LoanEntity;
import com.example.springstudy.Entity.ReservationEntity;
import com.example.springstudy.Entity.UserEntity;
import com.example.springstudy.Repository.BookRepository;
import com.example.springstudy.Repository.LoanRepository;
import com.example.springstudy.Repository.ReservationRepository;
import com.example.springstudy.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
  private static final int MAX_LOANS_PER_BOOK = 3;
  private static final int MAX_LOANS_PER_USER = 3;

  private final LoanRepository loanRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

  @Autowired
  public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
    this.loanRepository = loanRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
    this.reservationRepository = reservationRepository;
    this.reservationService = reservationService;
  }

  @Override
  public LoanDTO createLoan(LoanDTO loanDTO) {
    Long bookId = loanDTO.getBook();
    Long userId = loanDTO.getUser();

    if (isBookAlreadyLoanedByUser(userId, bookId)) {
      throw new AlreadyLoanedException("이미 동일한 도서를 대출한 이력이 있습니다.");
    }

    if (!isBookAvailableForLoan(bookId)) {
      throw new MaxLoansExceededException("도서의 대출 가능한 인원을 초과하였습니다.");
    }

    if (hasUserExceededMaxLoans(userId)) {
      throw new MaxLoansExceededException("사용자가 대출 가능한 최대 권수(3권)를 초과하였습니다.");
    }


    BookEntity bookEntity = bookRepository.getReferenceById(bookId);
    UserEntity userEntity = userRepository.getReferenceById(userId);
    LocalDateTime now = LocalDateTime.now();
    LoanEntity loanEntity = LoanEntity.builder()
        .book(bookEntity)
        .user(userEntity)
        .loanDate(now)
        .returnDate(now.plusDays(7))
        .build();
    loanRepository.save(loanEntity);

    bookEntity.setCurrentLoans(bookEntity.getCurrentLoans() + 1);
    userEntity.setCurrentLoans(userEntity.getCurrentLoans() + 1);
    bookRepository.save(bookEntity);
    userRepository.save(userEntity);

    return mapLoanEntityToDTO(loanEntity);
  }

  public class AlreadyLoanedException extends RuntimeException {
    public AlreadyLoanedException(String message) {
      super(message);
    }
  }

  public class MaxLoansExceededException extends RuntimeException {
    public MaxLoansExceededException(String message) {
      super(message);
    }
  }

  @Override
  public List<LoanDTO> getLoansByUser(Long userId) {
    List<LoanEntity> loanEntities = loanRepository.findByUserId(userId);
    return loanEntities.stream()
        .map(this::mapLoanEntityToDTO)
        .collect(Collectors.toList());
  }

  private boolean isBookAlreadyLoanedByUser(Long userId, Long bookId) {
    return loanRepository.existsByUserIdAndBookId(userId, bookId);
  }

  private boolean isBookAvailableForLoan(Long bookId) {
    BookEntity bookEntity = bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));
    return bookEntity.getCurrentLoans() < bookEntity.getMaxLoans();
  }

  private boolean hasUserExceededMaxLoans(Long userId) {
    int currentLoans = loanRepository.countByUserId(userId);
    return currentLoans >= MAX_LOANS_PER_USER;
  }

  private LoanDTO mapLoanEntityToDTO(LoanEntity loanEntity) {
    return LoanDTO.builder()
        .book(loanEntity.getBook().getId())
        .user(loanEntity.getUser().getId())
        .loanDate(loanEntity.getLoanDate())
        .returnDate(loanEntity.getReturnDate())
        .build();

  }


  @Override
  public List<LoanDTO> readAll() {
    List<LoanEntity> loanEntities = loanRepository.findAll();
    List<LoanDTO> loanDTOs = new ArrayList<>();

    for (LoanEntity loanEntity : loanEntities) {
      LoanDTO loanDTO = new LoanDTO();
      loanDTO.setBook(loanEntity.getBook().getId());
      loanDTO.setUser(loanEntity.getUser().getId());
      loanDTO.setLoanDate(loanEntity.getLoanDate());
      loanDTO.setReturnDate(loanEntity.getReturnDate());
      loanDTOs.add(loanDTO);
    }

    return loanDTOs;
  }

  @Override
  public List<BookDTO> getBooksByUserId(Long userId) {
    UserEntity user = userRepository.getReferenceById(userId);

    List<LoanEntity> loans = user.getLoans();
    List<BookDTO> bookDTOs = new ArrayList<>();

    // 각 대출 기록에 대해
    for (LoanEntity loanEntity : loans) {
      // 대출된 책 정보를 가져옵니다.
      BookEntity book = loanEntity.getBook();
      if (book != null) {
        // 새로운 BookDTO 객체를 생성하고 책 정보를 설정합니다.
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublishedDate(book.getPublishedDate());
        // 리스트에 추가합니다.
        bookDTOs.add(bookDTO);
      }
    }

    return bookDTOs;
  }


  public String returnBook(Long id) {
    // 대출 ID로 대출 정보를 조회
    Optional<LoanEntity> loanOptional = loanRepository.findById(id);
    if (loanOptional.isPresent()) {
      LoanEntity loanEntity = loanOptional.get();
      BookEntity bookEntity = loanEntity.getBook();
      UserEntity userEntity = loanEntity.getUser();

      // 책이 반납 가능한지 확인
      if (bookEntity.isAvailableForReturn()) { // 수정된 부분
        return "책이 이미 반납되었거나 반납할 수 없는 상태입니다.";
      }

      // 대출 정보 삭제
      loanRepository.delete(loanEntity);

      // 책 및 사용자 정보 업데이트
      bookEntity.setCurrentLoans(bookEntity.getCurrentLoans() - 1);
      userEntity.setCurrentLoans(userEntity.getCurrentLoans() - 1);
      bookRepository.save(bookEntity);
      userRepository.save(userEntity);

      // 다음 예약자에게 자동 대출 처리
      ReservationEntity nextReservation = reservationService.getNextReservation(bookEntity.getId());
      if (nextReservation != null) {
        createLoan(new LoanDTO(nextReservation.getBook(), nextReservation.getUser()));
        reservationService.deleteReservation(nextReservation);
        return "반납이 완료되었으며, 다음 예약자에게 자동으로 대출되었습니다.";
      }

      return "반납이 완료되었습니다.";
    } else {
      return "해당 대출 정보를 찾을 수 없습니다.";
    }
  }


  private boolean isBookReturned(BookEntity bookEntity) {
    // 책이 이미 반납되었는지 여부를 확인하여 반환
    return !bookEntity.isBorrowed();
  }
}
