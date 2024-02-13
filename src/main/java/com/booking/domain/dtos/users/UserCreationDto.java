package com.booking.domain.dtos.users;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreationDto {

    private String username;

    private String password;

    private String email;

    private AddressCreationDto addressCreationDto;

    private String role;

    private boolean isActive;
}
