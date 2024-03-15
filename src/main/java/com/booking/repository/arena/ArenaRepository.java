package com.booking.repository.arena;

import com.booking.domain.entities.arena.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {

    @Query("select a from Arena a join a.arenaInfo ai join ai.address ad where ad.city = :city")
    Set<Arena> findByCity(@Param("city") String city);


    @Query(value = "SELECT * FROM Arena a WHERE a.id IN :arenaIds AND NOT EXISTS (" +
            "SELECT 1 FROM reservation_arena ra WHERE ra.arena_id = a.id AND ra.booking_from < :to AND ra.booking_to > :from)",
            nativeQuery = true)
    Set<Arena> findAvailableArenasWithinGivenArenas(@Param("arenaIds") List<Long> arenaIds, @Param("from") Instant from, @Param("to") Instant to);


}
