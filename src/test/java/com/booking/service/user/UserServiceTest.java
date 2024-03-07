package com.booking.service.user;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.dtos.users.UserCreationDto;
import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import com.booking.domain.entities.address.Address;
import com.booking.domain.entities.user.Role;
import com.booking.domain.entities.user.User;
import com.booking.exception.NotFoundException;
import com.booking.repository.role.RoleRepository;
import com.booking.repository.user.UserRepository;
import com.booking.service.AddressService;
import com.booking.service.email.EmailService;
import com.booking.service.email.EmailVerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AddressService addressService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private EmailVerificationService emailVerificationService;
    @Mock
    private ModelMapper modelMapper;


    @InjectMocks
    private UserService userService;

    // Test data objects
    private UserCreationDto userCreationDto;
    private User user;
    private UserResultDto userResultDto;
    private Role role;
    private Address address;
    private AddressCreationDto addressCreationDto;
    private AddressResultDto addressResultDto;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("USER");

        address = new Address();
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("Anytown");

        user = new User();
        user.setUserId(1L);
        user.setUsername("john.doe");
        user.setPassword("password");
        user.setEmail("john.doe@example.com");
        user.setRole(role);
        user.setAddress(address);

        addressCreationDto = new AddressCreationDto("123 Main St", "Anytown", 0.0, 0.0);
        userCreationDto = new UserCreationDto("john.doe", "password", "john.doe@example.com", addressCreationDto, "USER", false);

        addressResultDto = new AddressResultDto("123 Main St", "Anytown", 0.0, 0.0);
        userResultDto = new UserResultDto("john.doe", "password", "john.doe@example.com", addressResultDto, "USER", false);
    }

    @Test
    void whenCreateUser_thenUserIsCreated() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(addressService.createForUser(any(AddressCreationDto.class))).thenReturn(address);
        when(modelMapper.map(any(UserCreationDto.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserResultDto.class))).thenReturn(userResultDto);

        UserResultDto result = userService.create(userCreationDto);

        assertNotNull(result);
        assertEquals(userResultDto.getUsername(), result.getUsername());
        verify(emailService).send(eq(userCreationDto.getEmail()), anyString(), anyString());
    }

    @Test
    void whenGetAllUsers_thenAllUsersAreReturned() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(User.class), eq(UserResultDto.class))).thenReturn(userResultDto);

        List<UserResultDto> result = userService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userResultDto.getUsername(), result.get(0).getUsername());
    }
    @Test
    void whenGetUserByUsername_thenUserIsReturned() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserResultDto.class))).thenReturn(userResultDto);

        UserResultDto result = userService.getUserByUsername("john.doe");

        assertNotNull(result);
        assertEquals("john.doe", result.getUsername());
    }
    @Test
    void whenGetById_thenUserIsReturned() {
        when(userRepository.getUserByUserId(anyLong())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserResultDto.class))).thenReturn(userResultDto);

        UserResultDto result = userService.getById(1L);

        assertNotNull(result);
        assertEquals("john.doe", result.getUsername());
    }
    @Test
    void whenUpdateUser_thenUserIsUpdated() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("newName", "newPassword", "newEmail@example.com", new AddressUpdateDto("New Street", "New City", 0.1, 0.1), "USER", true);
        when(userRepository.getUserByUserId(anyLong())).thenReturn(Optional.of(user));
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        when(modelMapper.map(any(UserUpdateDto.class), eq(User.class))).thenReturn(user);
        when(modelMapper.map(any(AddressUpdateDto.class), eq(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserResultDto.class))).thenReturn(userResultDto);

        UserResultDto result = userService.update(1L, userUpdateDto);

        assertNotNull(result);
        assertEquals("newName", result.getUsername());
        assertEquals("newEmail@example.com", result.getEmail());
        assertTrue(result.isActive());
    }
    @Test
    void whenVerificationWithCorrectCode_thenUserIsActive() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(emailVerificationService.getVerifyCode()).thenReturn("12345");
        when(emailVerificationService.getExpiredDate()).thenReturn(Instant.now().plusSeconds(3600));
        user.setActive(false);

        boolean result = userService.verification(1L, "12345");

        assertTrue(result);
        assertTrue(user.isActive());
    }
    @Test
    void whenResendVerificationCode_thenEmailIsSent() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(emailService).send(anyString(), anyString(), anyString());

        userService.resendVerificationCode(1L);

        verify(emailService).send(eq(user.getEmail()), anyString(), anyString());
    }
    @Test
    void whenDeleteUser_thenUserIsDeleted() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(anyLong());

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void whenDeleteNonExistingUser_thenThrowNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> userService.delete(1L));

        assertEquals("User with this id not found!", exception.getMessage());
    }

}
