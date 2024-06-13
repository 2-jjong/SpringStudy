package com.example.backend_assignment1_bookloansystem.Repository;

import com.example.backend_assignment1_bookloansystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
