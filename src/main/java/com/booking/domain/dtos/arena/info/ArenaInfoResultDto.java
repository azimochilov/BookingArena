package com.booking.domain.dtos.arena.info;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
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
    private AddressResultDto address;
}
