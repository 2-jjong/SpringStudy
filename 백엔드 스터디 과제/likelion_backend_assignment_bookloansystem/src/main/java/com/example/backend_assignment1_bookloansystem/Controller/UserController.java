package com.example.backend_assignment1_bookloansystem.Controller;

import com.example.backend_assignment1_bookloansystem.DTO.UserDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Entity.User;
import com.example.backend_assignment1_bookloansystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());
        UserDTO savedUserDTO = userService.addUser(userDTO);
        return new User(savedUserDTO.getId(), savedUserDTO.getName(), savedUserDTO.getEmail());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userDTO -> new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}/loans")
    public List<Loan> getLoansByUserId(@PathVariable Long id) {
        return userService.getLoansByUserId(id);
    }
}


