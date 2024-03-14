package com.booking.repository.arena;

import com.booking.domain.entities.arena.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {

    @Query("select a from Arena a join a.arenaInfo ai join ai.address ad where ad.city = :city")
    List<Arena> findByCity(@Param("city") String city);

    @Query("select a from Arena a where a.id in (select ra.arena.id from ReservationArena ra where ra.bookingFrom <= :to and ra.bookingTo >= :from)")
    List<Arena> findByReservationTime(@Param("from") Instant from, @Param("to") Instant to);
    @Query(value = "SELECT a.*, calculate_distance(:lat, :lng, addr.latitude, addr.longitude) as distance " +
            "FROM arena a " +
            "JOIN arena_info ai ON a.id = ai.arena_id " +
            "JOIN addresses addr ON ai.address_id = addr.id " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Arena> findByDistanceSorted(@Param("lat") Double lat, @Param("lng") Double lng);

}
