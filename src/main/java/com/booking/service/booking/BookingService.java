package com.booking.service.booking;


import com.booking.domain.dtos.booking.ReservationArenaCreationDto;
import com.booking.domain.dtos.booking.ReservationArenaResultDto;

import java.util.List;

public interface BookingService {
    ReservationArenaResultDto bookArena(ReservationArenaCreationDto reservationArenaCreationDto);
    List<ReservationArenaResultDto> getAll();

}
