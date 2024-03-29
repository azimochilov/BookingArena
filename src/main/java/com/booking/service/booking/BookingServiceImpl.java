package com.booking.service.booking;

import com.booking.domain.dtos.booking.ReservationArenaCreationDto;
import com.booking.domain.dtos.booking.ReservationArenaResultDto;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.booking.Booking;
import com.booking.domain.entities.user.User;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.booking.BookingRepository;
import com.booking.repository.user.UserRepository;
import com.booking.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    private final ArenaRepository arenaRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    @Override
    public ReservationArenaResultDto bookArena(ReservationArenaCreationDto reservationArenaCreationDto) {
        Arena arena = arenaRepository.findById(reservationArenaCreationDto.getArenaId()).orElseThrow(
                () -> new NotFoundException("not found with given id")
        );

        Booking reservationArena = new Booking();
        if (getByReservationByTime(arena, reservationArenaCreationDto.getBookingFrom(), reservationArenaCreationDto.getBookingTo())) {
            reservationArena.setArena(arena);
            reservationArena.setBookingTo(reservationArenaCreationDto.getBookingTo());
            reservationArena.setBookingFrom(reservationArenaCreationDto.getBookingFrom());
            reservationArena.setCreatedAt(Instant.now());
            reservationArena.setDescription(reservationArenaCreationDto.getDescription());
            reservationArena.setCostumer(reservationArenaCreationDto.getCostumer());

        }else{
            throw new NotFoundException("the arena is booked at this time");
        }

        long totalMinutes = ChronoUnit.MINUTES.between(reservationArenaCreationDto.getBookingFrom(), reservationArenaCreationDto.getBookingTo());
        int reservationTime = (int) (totalMinutes / 60);
        if (totalMinutes % 60 > 0) {
            reservationTime++;
        }

        int totalPrice = arena.getArenaInfo().getPrice() * reservationTime;
        reservationArena.setTotalPrice(Math.abs(totalPrice));

        Long userId = SecurityUtils.getCurrentUserId();
        User user  = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("couldnot fetch the user from the given token")
        );

        reservationArena.setUser(user);
        reservationArena.setConsumer(SecurityUtils.getCurrentUsername());

        bookingRepository.save(reservationArena);
        return modelMapper.map(reservationArena, ReservationArenaResultDto.class);
    }

    @Override
    public List<ReservationArenaResultDto> getAll() {
        List<Booking> arenas = bookingRepository.findAll();
        List<ReservationArenaResultDto> reservationArenaResultDTOs = arenas.stream()
                .map(arena -> modelMapper.map(arena, ReservationArenaResultDto.class))
                .collect(Collectors.toList());
        return reservationArenaResultDTOs;
    }

    private boolean getByReservationByTime(Arena arena, Instant from, Instant to) {
        return arena.getReservationArena().stream().noneMatch(a -> a.getBookingFrom().isBefore(to) && a.getBookingTo().isAfter(from));
    }
}
