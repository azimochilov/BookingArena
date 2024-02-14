package com.booking.service.user;

import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.users.UserCreationDto;
import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import com.booking.domain.entities.Address;
import com.booking.domain.entities.Role;
import com.booking.domain.entities.User;
import com.booking.exception.NotFoundException;
import com.booking.mapper.AddressMapper;
import com.booking.mapper.UserMapper;
import com.booking.repository.RoleRepository;
import com.booking.repository.UserRepository;
import com.booking.service.address.AddressService;
import com.booking.service.email.EmailService;
import com.booking.service.email.EmailVerificationService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@Data
@Builder
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailVerificationService emailVerificationService;
    @Override
    public UserResultDto create(UserCreationDto userDto) {

        Role role = roleRepository.findByName("USER").orElseThrow(
                () -> new NotFoundException("Not fit any role at all")
        );

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        addressService.create(userDto.getAddressCreationDto());

        User user = UserMapper.INSTANCE.userCreationToUser(userDto);
        userRepository.save(user);

        emailService.send(userDto.getEmail(),"Verify Code", emailVerificationService.generateCode());

        return UserMapper.INSTANCE.userCreationToUserResult(userDto);
    }

    @Override
    public List<UserResultDto> getAll() {
        List<User> users = userRepository.getAll();
        return UserMapper.INSTANCE.usersToUserResultDtos(users);
    }

    @Override
    public UserResultDto getUserByUsername(String username) {

        User user = userRepository.getUserByUsername(username).orElseThrow(
                () -> new NotFoundException("user not found with given username")
        );

        return UserMapper.INSTANCE.userToUserResult(user);
    }

    @Override
    public UserResultDto getById(Long id) {

        User user = userRepository.getUserByUserId(id).orElseThrow(
                () -> new NotFoundException("user not found with given id")
        );

        return UserMapper.INSTANCE.userToUserResult(user);
    }

    @Override
    public UserResultDto update(Long id, UserUpdateDto userDto) {
        User user = userRepository.getUserByUserId(id).orElseThrow(
                () -> new NotFoundException("User not found with this id! ")
        );

        Role role = roleRepository.findByName(userDto.getRole()).orElseThrow(
                () -> new NotFoundException("Not found given role")
        );

        user.setRole(role);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setAddress(AddressMapper.INSTANCE.addressUpdateDtoToAddress(userDto.getAddressUpdateDto()));
        user.setActive(userDto.isActive());

        userRepository.save(user);

        return UserMapper.INSTANCE.userToUserResult(user);
    }

    @Override
    public boolean verification(Long id, String code) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with this id not found")
        );
        if (user.isActive()) {
            return false;
        }
        Instant now = Instant.now();
        try {
            if (!now.isBefore(emailVerificationService.getExpiredDate())) {
                return false;
            }
        } catch (Exception e) {
            throw new NotFoundException(
                    "verification time expired"
            );
        }
        if (!code.equals(emailVerificationService.getVerifyCode())) {
            return false;
        }

        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public void resendVerificationCode(Long id) {
        emailService.send(userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id %s not found", id)
        )).getEmail(), "Verify Code", emailVerificationService.generateCode());

    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {

            userRepository.deleteById(id);

        } else {

            throw new NotFoundException("User with this id not found!");

        }
    }

}
