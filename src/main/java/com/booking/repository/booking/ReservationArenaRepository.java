package com.booking.repository.booking;

import com.booking.domain.entities.booking.ReservationArena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationArenaRepository extends JpaRepository<ReservationArena, Long> {
}
