package com.booking.repository;

import com.booking.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByUserId(Long id);
}
