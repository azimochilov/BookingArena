package com.booking.api.user;

import com.booking.service.secure.LogoutManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutManagerService logoutManagerService;

    @GetMapping
    public void logout() {
        logoutManagerService.logout();
    }
}
