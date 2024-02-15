package com.booking.service.secure;

import com.booking.domain.dtos.login.LoginResponseDto;
import com.booking.security.JwtIssuer;
import com.booking.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginManagerService {

    private final AuthenticationManager authenticationManager;
    private final JwtIssuer jwtIssuer;

    public LoginResponseDto attemptLogin(String username, String password) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);

        return LoginResponseDto.builder().accessToken(token).build();
    }
}
