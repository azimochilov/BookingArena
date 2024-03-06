package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.address.Address;
import com.booking.exception.NotFoundException;
import com.booking.repository.address.AddressRepository;
import com.booking.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressService addressService;

    // Sample data for testing
    private AddressCreationDto addressCreationDto;
    private AddressUpdateDto addressUpdateDto;
    private Address address;
    private AddressResultDto addressResultDto;

    @BeforeEach
    public void setUp() {
        addressCreationDto = new AddressCreationDto("123 Main St", "Anytown", 40.7128, -74.0060);
        addressUpdateDto = new AddressUpdateDto("456 Park Ave", "New City", 40.1234, -73.9876);
        address = new Address(1L, "123 Main St", "Anytown", 40.7128, -74.0060);
        addressResultDto = new AddressResultDto("123 Main St", "Anytown", 40.7128, -74.0060);

        lenient().when(modelMapper.map(addressCreationDto, Address.class)).thenReturn(address);
        lenient().when(modelMapper.map(address, AddressResultDto.class)).thenReturn(addressResultDto);
        lenient().when(modelMapper.map(addressUpdateDto, Address.class)).thenReturn(address);
    }


    @Test
    public void whenCreateAddress_thenPropertiesMatch() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResultDto createdAddress = addressService.create(addressCreationDto);

        assertAll(
                () -> assertEquals(addressResultDto.getStreet(), createdAddress.getStreet()),
                () -> assertEquals(addressResultDto.getCity(), createdAddress.getCity()),
                () -> assertEquals(addressResultDto.getLatitude(), createdAddress.getLatitude()),
                () -> assertEquals(addressResultDto.getLongitude(), createdAddress.getLongitude())
        );
    }

    @Test
    public void whenCreateForUser_thenPropertiesMatch() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address createdAddress = addressService.createForUser(addressCreationDto);

        assertAll(
                () -> assertEquals(address.getStreet(), createdAddress.getStreet()),
                () -> assertEquals(address.getCity(), createdAddress.getCity()),
                () -> assertEquals(address.getLatitude(), createdAddress.getLatitude()),
                () -> assertEquals(address.getLongitude(), createdAddress.getLongitude())
        );
    }

    @Test
    public void whenUpdateExistingAddress_thenPropertiesAreUpdated() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResultDto updatedAddress = addressService.update(1L, addressUpdateDto);

        assertAll(
                () -> assertEquals(addressResultDto.getStreet(), updatedAddress.getStreet()),
                () -> assertEquals(addressResultDto.getCity(), updatedAddress.getCity()),
                () -> assertEquals(addressResultDto.getLatitude(), updatedAddress.getLatitude()),
                () -> assertEquals(addressResultDto.getLongitude(), updatedAddress.getLongitude())
        );
    }

    @Test
    public void whenGetById_thenPropertiesMatch() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        AddressResultDto foundAddress = addressService.getById(1L);

        assertAll(
                () -> assertEquals(addressResultDto.getStreet(), foundAddress.getStreet()),
                () -> assertEquals(addressResultDto.getCity(), foundAddress.getCity()),
                () -> assertEquals(addressResultDto.getLatitude(), foundAddress.getLatitude()),
                () -> assertEquals(addressResultDto.getLongitude(), foundAddress.getLongitude())
        );
    }

    @Test
    public void whenGetByName_thenPropertiesMatch() {
        when(addressRepository.findByCity("Anytown")).thenReturn(Optional.of(address));

        AddressResultDto foundAddress = addressService.getByName("Anytown");

        assertAll(

                () -> assertEquals(addressResultDto.getStreet(), foundAddress.getStreet()),
                () -> assertEquals(addressResultDto.getCity(), foundAddress.getCity()),
                () -> assertEquals(addressResultDto.getLatitude(), foundAddress.getLatitude()),
                () -> assertEquals(addressResultDto.getLongitude(), foundAddress.getLongitude())
        );
    }

    @Test
    public void whenDeleteExistingAddress_thenNoExceptionThrown() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).delete(address);

        assertDoesNotThrow(() -> addressService.delete(1L));
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    public void whenDeleteNonExistingAddress_thenThrowNotFoundException() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> addressService.delete(1L));
        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    public void whenGetByIdNonExisting_thenThrowNotFoundException() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> addressService.getById(1L));
        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    public void whenGetByNameNonExisting_thenThrowNotFoundException() {
        when(addressRepository.findByCity("NonExistingCity")).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> addressService.getByName("NonExistingCity"));
        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    public void whenUpdateNonExistingAddress_thenThrowNotFoundException() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> addressService.update(1L, addressUpdateDto));
        assertEquals("Address not found", exception.getMessage());
    }
}
