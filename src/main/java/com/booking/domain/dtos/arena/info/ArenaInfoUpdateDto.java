package com.booking.domain.dtos.arena.info;

import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArenaInfoUpdateDto {
    private String phone;
    private Integer price;
    private Instant workedFrom;
    private Instant workedTo;
    private Instant createdAt;
    private Instant updatedAt;
    private AddressUpdateDto address;
}
