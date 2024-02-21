package com.booking.repository;

import com.booking.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AddressRepository extends JpaRepository<Address , Long> {
//    Optional<Address> getById(Long id);
    Optional<Address> findByCity(String name);
}
