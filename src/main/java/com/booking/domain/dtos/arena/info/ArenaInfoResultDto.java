package com.booking.domain.dtos.arena.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArenaInfoResultDto {
    private String phone;
    private Integer price;
    private LocalTime workedFrom;
    private LocalTime workedTo;
    private Instant createdAt;
    private Instant updatedAt;
    private String street;
    private String city;
    private Double longitude;
    private Double latitude;
}
