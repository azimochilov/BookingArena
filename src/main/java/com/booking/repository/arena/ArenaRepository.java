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


    @Query(value = "SELECT a.*, calculate_distance(ai.latitude, ai.longitude, :lat, :lng) as distance " +
            "FROM arena a " +
            "JOIN arena_info ai ON a.id = ai.arena_id " +
            "WHERE (:city IS NULL OR ai.city = :city) " +
            "AND NOT EXISTS ( " +
            "SELECT * FROM booking b " +
            "WHERE b.arena_id = a.id AND b.booking_from < :to AND b.booking_to > :from) " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Arena> findFilteredAndSortedArenas(@Param("city") String city, @Param("lat") Double lat, @Param("lng") Double lng, @Param("from") Instant from, @Param("to") Instant to);
}

