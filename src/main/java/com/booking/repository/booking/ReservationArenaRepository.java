package com.booking.repository.booking;

import com.booking.domain.entities.booking.ReservationArena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationArenaRepository extends JpaRepository<ReservationArena, Long> {
    List<ReservationArena> findByArenaId(Long arenaId);
}
