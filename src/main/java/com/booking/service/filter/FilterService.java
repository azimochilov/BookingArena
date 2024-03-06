package com.booking.service.filter;

import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import com.booking.domain.entities.address.Address;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.domain.entities.booking.ReservationArena;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.booking.ReservationArenaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FilterService {
    private final ReservationArenaRepository reservationArenaRepository;
    private final ArenaRepository arenaRepository;
    private final ModelMapper modelMapper;

    public List<ArenaResultDto> getByFilter(FiltersDto filters) {
        Set<Arena> arenas;
        if (filters.getCity() != null) {
            arenas = getByCity(arenaRepository.findAll(), filters.getCity());
        } else {
            arenas = new HashSet<>(arenaRepository.findAll());
        }

        if (filters.getFrom() != null && filters.getTo() != null) {
            arenas = getByReservationByTime(arenas, filters.getFrom(), filters.getTo());
        }
        if (filters.getLongitude() != null && filters.getLatitude() != null) {
            arenas = getSortedByDistance(arenas, filters.getLongitude(), filters.getLatitude());
        }

        List<ArenaResultDto> resultDtos = new ArrayList<>();
        for (Arena arena : arenas) {
            ArenaResultDto dto = modelMapper.map(arena, ArenaResultDto.class);
            resultDtos.add(dto);
        }

        return resultDtos;
    }
    private Set<Arena> getByCity(List<Arena> all, String city) {
        Set<Arena> filtered = new HashSet<>();
        for (Arena arena : all) {
            ArenaInfo arenaInfo = arena.getArenaInfo();
            if (arenaInfo != null) {
                Address address = arenaInfo.getAddress();
                if (address != null && city.equals(address.getCity())) {
                    filtered.add(arena);
                }
            }
        }
        return filtered;
    }

    private Set<Arena> getByReservationByTime(Set<Arena> arenas, Instant from, Instant to) {
        return arenas.stream()
                .filter(arena -> arena.getReservationArena().stream()
                        .noneMatch(r -> r.getBookingFrom().isBefore(to) && r.getBookingTo().isAfter(from)))
                .collect(Collectors.toSet());
    }


    private Set<Arena> getSortedByDistance(Set<Arena> filtered, Double lng, Double lat) {
        Map<Arena, Double> distance = new HashMap<>();
        for (Arena arena : filtered) {
            distance.put(arena, Point2D.distance(arena.getArenaInfo().getAddress().getLongitude()
                    , arena.getArenaInfo().getAddress().getLatitude(), lng, lat));
        }
        return distance.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
