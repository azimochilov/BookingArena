package com.booking.domain.dtos.login;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private String accessToken;
}