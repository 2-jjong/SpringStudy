package com.example.springstudy.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

//import 단축키 com + enter
@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스랑 동일 개념 , 마리아 디비 : 인크리???????
  private Long id;
  @Column
  private String username;
  @Column
  private String email;
  @Column
  private Long password;
  private int currentLoans = 0; // 현재 대출 중인 도서 수 초기화



//양방향
  @OneToMany(mappedBy = "user")
  private List<LoanEntity> loans;


  @Builder
  public UserEntity(String username, String email, Long password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public UserEntity(Long userId) {
  }


  public void updateName(String username) {

    this.username = username;
  }

  public void updateEmail(String email) {

    this.email = email;
  }

  public void updatePassword(Long password) {

    this.password=password;
  }

  public boolean hasBooksBorrowed() {
    // 사용자가 대출한 도서 목록을 확인하고, 대출한 도서가 있는지 확인
    return !loans.isEmpty();
  }

  public int getCurrentLoans() {
    return currentLoans;
  }

  public void setCurrentLoans(int currentLoans) {
    this.currentLoans = currentLoans;
  }


}