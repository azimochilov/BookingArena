package com.booking.repository;

import com.booking.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getAll();
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByUserId(Long id);
}
