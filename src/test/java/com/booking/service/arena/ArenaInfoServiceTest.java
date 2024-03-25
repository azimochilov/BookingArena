package com.booking.service.arena;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.repository.arena.ArenaInfoRepository;
import com.booking.service.address.AddressService;
import com.booking.service.arena.info.ArenaInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class ArenaInfoServiceTest {
    @Mock
    private ArenaInfoRepository arenaInfoRepository;

    @Mock
    private AddressService addressService;

    @Mock(lenient = true)
    private ModelMapper modelMapper;

    @InjectMocks
    private ArenaInfoServiceImpl arenaInfoService;

    private ArenaInfo arenaInfo;
    private ArenaInfoCreationDto arenaInfoCreationDto;
    private ArenaInfoResultDto arenaInfoResultDto;
    private ArenaInfoUpdateDto arenaInfoUpdateDto;
    private AddressCreationDto addressCreationDto;
    private AddressResultDto addressResultDto;


    @BeforeEach
    public void setUp() {
        addressCreationDto = new AddressCreationDto("Street 1", "City A", 10.0, 20.0);
        addressResultDto = new AddressResultDto("Street 1", "City A", 10.0, 20.0);

        arenaInfoCreationDto = new ArenaInfoCreationDto("123456789", 200, LocalTime.of(9, 0), LocalTime.of(17, 0), addressCreationDto);
        arenaInfoResultDto = new ArenaInfoResultDto("123456789", 200, LocalTime.of(9, 0), LocalTime.of(17, 0), Instant.now(), Instant.now(), addressResultDto);
        arenaInfoUpdateDto = new ArenaInfoUpdateDto("123456789", 200, Instant.now().minusSeconds(3600), Instant.now(), Instant.now(), Instant.now(), new AddressUpdateDto("Street 2", "City B", 30.0, 40.0));

        arenaInfo = new ArenaInfo();
        arenaInfo.setId(1L);
        arenaInfo.setPhone("123456789");
        arenaInfo.setPrice(100);
        arenaInfo.setWorkedFrom(Instant.now().minusSeconds(3600));
        arenaInfo.setWorkedTo(Instant.now());
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City A");
        address.setLatitude(10.0);
        address.setLongitude(20.0);
        arenaInfo.setAddress(address);

        when(modelMapper.map(any(ArenaInfoCreationDto.class), eq(ArenaInfo.class))).thenReturn(arenaInfo);
        when(modelMapper.map(any(ArenaInfo.class), eq(ArenaInfoResultDto.class))).thenReturn(arenaInfoResultDto);
        when(modelMapper.map(any(ArenaInfoUpdateDto.class), eq(ArenaInfo.class))).thenReturn(arenaInfo);
        when(modelMapper.map(eq(arenaInfoCreationDto), eq(ArenaInfo.class))).thenReturn(arenaInfo);
        when(modelMapper.map(eq(arenaInfo), eq(ArenaInfoResultDto.class))).thenReturn(arenaInfoResultDto);
        when(modelMapper.map(eq(arenaInfoUpdateDto), eq(ArenaInfo.class))).thenAnswer(invocation -> {
            ArenaInfo source = arenaInfo;
            ArenaInfoUpdateDto updateDto = invocation.getArgument(0);
            source.setPhone(updateDto.getPhone());
            source.setPrice(updateDto.getPrice());
            return source;
        });
    }

    @Test
    public void whenCreateWithDto_thenPropertiesMatch() {
        when(addressService.create(any(AddressCreationDto.class))).thenReturn(addressResultDto);
        when(arenaInfoRepository.save(any(ArenaInfo.class))).thenReturn(arenaInfo);

        ArenaInfoResultDto createdArenaInfo = arenaInfoService.createWithDto(arenaInfoCreationDto);

        assertNotNull(createdArenaInfo);
        assertEquals(arenaInfoCreationDto.getPhone(), createdArenaInfo.getPhone());
        assertEquals(arenaInfoCreationDto.getPrice(), createdArenaInfo.getPrice());
        assertEquals(arenaInfoCreationDto.getAddress().getCity(), createdArenaInfo.getAddress().getCity());
    }

    @Test
    public void whenGetById_thenSuccess() {
        when(arenaInfoRepository.findById(anyLong())).thenReturn(Optional.of(arenaInfo));

        ArenaInfoResultDto foundArenaInfo = arenaInfoService.getById(1L);

        assertNotNull(foundArenaInfo);
        assertEquals(arenaInfo.getPhone(), foundArenaInfo.getPhone());
        assertEquals(arenaInfo.getPrice(), foundArenaInfo.getPrice());
    }

    @Test
    public void whenUpdate_thenSuccess() {
        Long arenaInfoId = 1L;
        arenaInfo.setPhone(arenaInfoUpdateDto.getPhone());
        arenaInfo.setPrice(arenaInfoUpdateDto.getPrice());

        Long addressId = arenaInfo.getAddress().getAddressId();

        when(arenaInfoRepository.findById(arenaInfoId)).thenReturn(Optional.of(arenaInfo));

        AddressUpdateDto updateDto = arenaInfoUpdateDto.getAddress();
        AddressResultDto updatedAddressResultDto = new AddressResultDto(
                updateDto.getStreet(), updateDto.getCity(), updateDto.getLongitude(), updateDto.getLatitude()
        );
        when(addressService.update(eq(addressId), eq(updateDto))).thenReturn(updatedAddressResultDto);

        when(arenaInfoRepository.save(any(ArenaInfo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArenaInfoResultDto result = arenaInfoService.update(1L, arenaInfoUpdateDto);

        assertNotNull(result);
        assertEquals(arenaInfoUpdateDto.getPhone(), result.getPhone());
        assertEquals(arenaInfoUpdateDto.getPrice(), result.getPrice());

        verify(arenaInfoRepository).findById(arenaInfoId);
        verify(addressService).update(eq(addressId), eq(updateDto));
        verify(arenaInfoRepository).save(any(ArenaInfo.class));
    }

}
