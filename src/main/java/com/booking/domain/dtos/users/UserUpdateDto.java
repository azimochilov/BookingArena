package com.booking.domain.dtos.users;

import com.booking.domain.dtos.addresses.AddressUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private String username;

    private String password;

    private String email;

    private AddressUpdateDto addressUpdateDto;

    private String role;

    private boolean isActive;
}
