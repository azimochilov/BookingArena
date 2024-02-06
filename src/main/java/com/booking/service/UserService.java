package com.booking.service;

import com.booking.domain.dtos.users.UserCreationDto;
import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService{
    @Override
    public UserResultDto create(UserCreationDto userDto) {
        return null;
    }

    @Override
    public List<UserResultDto> getAll() {
        return null;
    }

    @Override
    public UserResultDto getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserResultDto getById(Long id) {
        return null;
    }

    @Override
    public UserResultDto update(Long id, UserUpdateDto userDto) {
        return null;
    }

    @Override
    public boolean verification(Long id, String code) {
        return false;
    }

    @Override
    public void resendVerificationCode(Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
