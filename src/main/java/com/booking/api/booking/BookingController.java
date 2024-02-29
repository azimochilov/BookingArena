package com.booking.api.booking;

import com.booking.domain.dtos.booking.ReservationArenaCreationDto;
import com.booking.domain.dtos.booking.ReservationArenaResultDto;
import com.booking.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ReservationArenaResultDto> bookArena(@RequestBody ReservationArenaCreationDto reservationArenaCreationDto) {
        ReservationArenaResultDto result = bookingService.bookArena(reservationArenaCreationDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationArenaResultDto>> getAllBookings() {
        List<ReservationArenaResultDto> bookings = bookingService.getAll();
        return ResponseEntity.ok(bookings);
    }
}

