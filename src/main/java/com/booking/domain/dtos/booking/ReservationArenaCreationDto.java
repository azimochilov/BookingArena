package com.booking.domain.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationArenaCreationDto {
    private Instant createdAt;
    private Instant bookingFrom;
    private Instant bookingTo;
    private String description;
    private Integer totalPrice;
    private String costumer;
    private Long arenaId;
}
