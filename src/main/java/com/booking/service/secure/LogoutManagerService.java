package com.booking.service.secure;

import com.booking.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LogoutManagerService {
    private final JwtProperties jwtProperties;
    public void logout() {

        jwtProperties.setExpiration(Instant.now());
    }
}
