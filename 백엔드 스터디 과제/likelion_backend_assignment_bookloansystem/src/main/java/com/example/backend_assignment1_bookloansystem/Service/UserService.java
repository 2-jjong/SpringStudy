package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DTO.UserDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO); // 메서드 이름 수정
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id); // 메서드 이름 수정
    List<Loan> getLoansByUserId(Long id); // 새로운 메서드 추가
}


