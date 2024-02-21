package com.booking.api.user;


import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import com.booking.domain.dtos.users.VerifyDto;
import com.booking.domain.entities.User;
import com.booking.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booking.domain.dtos.users.UserCreationDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserCreationDto user){
        return ResponseEntity.ok(userService.create(user));
    }

//    private Boolean checkPasswordLength(String password){
//
//        return (password.length()>4);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDto) {
        UserResultDto user = userService.update(id, userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyUser(@PathVariable Long id, @RequestBody VerifyDto verifyDto){
        if (userService.verification(id, verifyDto.getCode())) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("Verification failed", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<?> resend(@PathVariable Long id){
        userService.resendVerificationCode(id);
        return ResponseEntity.ok().build();
    }

}
