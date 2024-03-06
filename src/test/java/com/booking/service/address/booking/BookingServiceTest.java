package com.booking.service.address.booking;

import com.booking.domain.dtos.booking.ReservationArenaCreationDto;
import com.booking.domain.dtos.booking.ReservationArenaResultDto;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.domain.entities.booking.Booking;
import com.booking.domain.entities.booking.ReservationArena;
import com.booking.domain.entities.user.User;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.booking.BookingRepository;
import com.booking.repository.booking.ReservationArenaRepository;
import com.booking.repository.user.UserRepository;
import com.booking.service.booking.BookingServiceImpl;
import com.booking.utils.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private ReservationArenaRepository reservationArenaRepository;

    @Mock
    private ArenaRepository arenaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock(lenient = true)
    private ModelMapper modelMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Arena arena;
    private ReservationArena reservationArena;
    private Booking booking;
    private ReservationArenaCreationDto reservationArenaCreationDto;

    @BeforeEach
    void setUp() {
        // Initialize user
        user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");
        arena = Arena.builder()
                .id(1L)
                .arenaInfo(ArenaInfo.builder().price(100).build())
                .build();

        reservationArena = ReservationArena.builder()
                .id(1L)
                .bookingFrom(Instant.now())
                .bookingTo(Instant.now().plusSeconds(3600))
                .description("Test booking")
                .totalPrice(200)
                .costumer("Customer Name")
                .arena(arena)
                .build();

        booking = Booking.builder()
                .id(1L)
                .consumer("Customer Name")
                .reservationArena(reservationArena)
                .user(user)
                .build();

        reservationArenaCreationDto = ReservationArenaCreationDto.builder()
                .bookingFrom(Instant.now())
                .bookingTo(Instant.now().plusSeconds(3600))
                .description("Test booking")
                .totalPrice(200)
                .costumer("Customer Name")
                .arenaId(1L)
                .build();

    }
    @Test
    void bookArena_Success() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            when(arenaRepository.findById(any())).thenReturn(Optional.of(arena));
            ReservationArenaResultDto reservArenaTest = bookingService.bookArena(reservationArenaCreationDto);
            assertAll(
                    () -> assertThat(reservArenaTest).isNotNull(),
                    () -> Assertions.assertEquals(reservArenaTest,(reservationArena))
            );
        }
    }


    @Test
    void bookArena_InvalidArenaId_Failure() {
        when(arenaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> bookingService.bookArena(reservationArenaCreationDto));

        assertEquals("not found with given id", exception.getMessage());
        // Verify that no booking attempt is made if the arena is not found
        verifyNoInteractions(bookingRepository);
    }


}
