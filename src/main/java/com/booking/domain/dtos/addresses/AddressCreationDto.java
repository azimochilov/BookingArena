package com.booking.domain.dtos.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressCreationDto {

    private String street;

    private String city;

    private Double longitude;

    private Double latitude;

}
