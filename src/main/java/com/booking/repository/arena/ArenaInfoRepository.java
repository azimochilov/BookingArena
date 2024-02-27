package com.booking.repository.arena;

import com.booking.domain.entities.arena.ArenaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArenaInfoRepository extends JpaRepository<ArenaInfo, Long> {
}
