package com.booking.service.filter;

import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.booking.ReservationArena;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.booking.ReservationArenaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FilterService {
    private final ReservationArenaRepository reservationArenaRepository;
    private final ArenaRepository arenaRepository;
    public List<Arena> findAvailableAndSortedArenas(Instant from, Instant to, double userLat, double userLon) {
        List<Arena> allArenas = arenaRepository.findAll();
        List<Arena> filteredArenas = allArenas.stream()
                .filter(arena -> isArenaAvailable(arena.getId(), from, to))
                .collect(Collectors.toList());


        filteredArenas.sort(Comparator.comparingDouble(arena ->
                calculateDistance(userLat, userLon, arena.getArenaInfo().getAddress().getLatitude(), arena.getArenaInfo().getAddress().getLongitude())));

        return filteredArenas;
    }

    private boolean isArenaAvailable(Long arenaId, Instant from, Instant to) {
        List<ReservationArena> reservations = reservationArenaRepository.findByArenaId(arenaId);
        return reservations.stream()
                .noneMatch(reservation -> reservation.getBookingFrom().isBefore(to) && reservation.getBookingTo().isAfter(from));
    }

    private double calculateDistance(double userLat, double userLon, double arenaLat, double arenaLon) {
        final int R = 6371;
        double latDistance = Math.toRadians(arenaLat - userLat);
        double lonDistance = Math.toRadians(arenaLon - userLon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(arenaLat)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }
}
