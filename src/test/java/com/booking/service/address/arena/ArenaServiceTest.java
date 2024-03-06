package com.booking.service.address.arena;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.arena.ArenaCreationDto;
import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.arena.ArenaUpdateDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
import com.booking.domain.entities.address.Address;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.domain.entities.user.User;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.user.UserRepository;
import com.booking.service.arena.ArenaServiceImpl;
import com.booking.service.arena.info.ArenaInfoService;
import com.booking.service.filesystem.ImageService;
import com.booking.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArenaServiceTest {


    @Mock
    private ArenaRepository arenaRepository;
    @Mock
    private ArenaInfoService arenaInfoService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageService imageService;
    @Mock(lenient = true)
    private ModelMapper modelMapper;

    @InjectMocks
    private ArenaServiceImpl arenaService;

    private Arena arena;
    private ArenaResultDto arenaResultDto;
    private ArenaCreationDto arenaCreationDto;
    private ArenaUpdateDto arenaUpdateDto;
    private User user;
    private ArenaInfo arenaInfo;
    private ArenaInfoCreationDto arenaInfoCreationDto;
    private ArenaInfoUpdateDto arenaInfoUpdateDto;
    private AddressCreationDto addressCreationDto;

    @BeforeEach
    void setUp() {
        // Mock setup for user, arenaInfo, and their DTOs
        user = new User();
        addressCreationDto = new AddressCreationDto();
        user.setUserId(1L);
        user.setUsername("user1");
        arenaInfo = ArenaInfo.builder().id(1L).phone("123456789").price(100).build();
        arenaInfoUpdateDto = ArenaInfoUpdateDto.builder().phone("987654321").price(200).build();

        ArenaInfo mockedArenaInfo = ArenaInfo.builder()
                .id(1L) // Assuming an ID for illustration
                .phone("123456789")
                .price(100)
                .address(new Address()) // Set a non-null Address here
                .workedFrom(Instant.now())
                .workedTo(Instant.now())
                .build();

        // Stub the modelMapper to return the mockedArenaInfo when mapping from ArenaCreationDto to ArenaInfo
        when(modelMapper.map(any(ArenaInfoCreationDto.class), eq(ArenaInfo.class))).thenReturn(mockedArenaInfo);

        arenaCreationDto = ArenaCreationDto.builder()
                .name("New Arena")
                .description("A new arena description")
                .status(true)
                .arenaInfo(arenaInfoCreationDto)
                .build();

        arenaUpdateDto = ArenaUpdateDto.builder()
                .name("Updated Arena")
                .description("Updated description")
                .status(true)
                .arenaInfo(arenaInfoUpdateDto)
                .build();

        arena = Arena.builder()
                .id(1L)
                .name("Arena")
                .description("Description")
                .status(true)
                .user(user)
                .arenaInfo(arenaInfo)
                .build();

        arenaResultDto = ArenaResultDto.builder()
                .name(arena.getName())
                .description(arena.getDescription())
                .status(arena.isStatus())
                .image("image.jpg")
                .build();

        LocalTime workedFrom = LocalTime.of(9, 0); // Example start time
        LocalTime workedTo = LocalTime.of(17, 0); // Example end time

        AddressCreationDto addressCreationDto = new AddressCreationDto("Street", "City", 0.0, 0.0);
        ArenaInfoCreationDto arenaInfoCreationDto = ArenaInfoCreationDto.builder()
                .phone("123456789")
                .price(100)
                .workedFrom(workedFrom)
                .workedTo(workedTo)
                .address(addressCreationDto)
                .build();

        arenaCreationDto = ArenaCreationDto.builder()
                .name("New Arena")
                .description("A new arena description")
                .status(true)
                .arenaInfo(arenaInfoCreationDto)
                .build();

        // Mock behavior of modelMapper for all mappings
        when(modelMapper.map(any(ArenaInfoCreationDto.class), eq(ArenaInfo.class))).thenReturn(new ArenaInfo());
        when(modelMapper.map(any(AddressCreationDto.class), eq(Address.class))).thenReturn(new Address());
        when(modelMapper.map(any(ArenaCreationDto.class), eq(Arena.class))).thenReturn(arena);

        when(modelMapper.map(any(ArenaCreationDto.class), eq(Arena.class))).thenReturn(arena);
        when(modelMapper.map(any(Arena.class), eq(ArenaResultDto.class))).thenReturn(arenaResultDto);
        when(modelMapper.map(any(ArenaUpdateDto.class), eq(Arena.class))).thenAnswer(invocation -> {
            ArenaUpdateDto dto = invocation.getArgument(0);
            arena.setName(dto.getName());
            arena.setDescription(dto.getDescription());
            return arena;
        });
        lenient().when(modelMapper.map(any(ArenaCreationDto.class), eq(Arena.class))).thenReturn(arena);
        lenient().when(modelMapper.map(any(Arena.class), eq(ArenaResultDto.class))).thenReturn(arenaResultDto);
        lenient().when(modelMapper.map(any(ArenaUpdateDto.class), eq(Arena.class))).thenAnswer(invocation -> {
            ArenaUpdateDto dto = invocation.getArgument(0);
            arena.setName(dto.getName());
            arena.setDescription(dto.getDescription());
            return arena;
        });
    }

    @Test
    void whenGetById_thenSuccess() {
        when(arenaRepository.findById(1L)).thenReturn(Optional.of(arena));

        ArenaResultDto result = arenaService.getById(1L);

        assertNotNull(result);
        assertEquals("Arena", result.getName());
    }

    @Test
    void whenGetById_thenNotFoundException() {
        when(arenaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> arenaService.getById(1L));
    }
    @Test
    void whenGetAll_thenSuccess() {
        when(arenaRepository.findAll()).thenReturn(List.of(arena));

        List<ArenaResultDto> results = arenaService.getAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
    @Test
    void whenCreate_thenSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);

        MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class);
        utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
        ArenaResultDto result = arenaService.create(arenaCreationDto);

        assertNotNull(result);
        assertEquals("New Arena", result.getName());
    }
    @Test
    void whenUpdate_thenSuccess() {
        when(arenaRepository.findById(1L)).thenReturn(Optional.of(arena));
        when(arenaInfoService.update(anyLong(), any(ArenaInfoUpdateDto.class))).thenReturn(new ArenaInfoResultDto());
        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);

        ArenaResultDto result = arenaService.update(1L, arenaUpdateDto, null);

        assertNotNull(result);
        assertEquals("Updated Arena", result.getName());
    }

    @Test
    void whenUpdate_thenNotFoundException() {
        when(arenaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> arenaService.update(1L, arenaUpdateDto, null));
    }
    @Test
    void whenDelete_thenSuccess() {
        when(arenaRepository.findById(1L)).thenReturn(Optional.of(arena));
        doNothing().when(arenaRepository).delete(any(Arena.class));

        assertDoesNotThrow(() -> arenaService.delete(1L));
    }

    @Test
    void whenDelete_thenNotFoundException() {
        when(arenaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> arenaService.delete(1L));
    }


}
