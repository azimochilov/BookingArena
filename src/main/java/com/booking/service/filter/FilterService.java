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
    private final ArenaRepository arenaRepository;
    private final ModelMapper modelMapper;
    public List<ArenaResultDto> getByFilter(FiltersDto filters) {
        List<Arena> arenas;

        if (filters.getCity() != null && !filters.getCity().isEmpty()) {
            arenas = arenaRepository.findByCity(filters.getCity());
        } else {
            arenas = arenaRepository.findAll();
        }

        if (filters.getFrom() != null && filters.getTo() != null) {
            List<Arena> arenasByTime = arenaRepository.findByReservationTime(filters.getFrom(), filters.getTo());
            arenas.retainAll(arenasByTime);
        }
        if (filters.getLongitude() != null && filters.getLatitude() != null) {
            arenas = arenaRepository.findByDistanceSorted(filters.getLatitude(), filters.getLongitude());
        }

        return arenas.stream()
                .map(arena -> modelMapper.map(arena, ArenaResultDto.class))
                .collect(Collectors.toList());
    }
}
