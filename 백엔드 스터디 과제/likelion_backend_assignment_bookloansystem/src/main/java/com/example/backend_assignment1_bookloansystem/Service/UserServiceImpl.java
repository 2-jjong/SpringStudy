package com.example.backend_assignment1_bookloansystem.Service;

import com.example.backend_assignment1_bookloansystem.DAO.LoanDAO;
import com.example.backend_assignment1_bookloansystem.DAO.UserDAO;
import com.example.backend_assignment1_bookloansystem.DTO.UserDTO;
import com.example.backend_assignment1_bookloansystem.Entity.Loan;
import com.example.backend_assignment1_bookloansystem.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final LoanDAO loanDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, LoanDAO loanDAO) {
        this.userDAO = userDAO;
        this.loanDAO = loanDAO;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userDAO.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = userDAO.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public List<Loan> getLoansByUserId(Long id) {
        return loanDAO.findByUserId(id);
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}


