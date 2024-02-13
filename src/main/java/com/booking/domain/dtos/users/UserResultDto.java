package com.booking.domain.dtos.users;

import com.booking.domain.dtos.addresses.AddressResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResultDto {
    private String username;

    private String password;

    private String email;

    private AddressResultDto addressResultDto;

    private String role;

    private boolean isActive;
}
