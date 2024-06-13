package com.example.backend_assignment1_bookloansystem.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    // Getters and Setters
    private Long id;
    private String name;
    private String email;

    // 기본 생성자
    public UserDTO() {}

    // 모든 필드를 포함하는 생성자
    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}

