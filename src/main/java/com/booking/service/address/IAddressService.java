package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.address.Address;

public interface IAddressService {
    AddressResultDto create(AddressCreationDto addressDto);
    Address createForUser(AddressCreationDto addressCreationDto);
    Address createForArena(Address address);
    AddressResultDto update(Long id, AddressUpdateDto addressDto);
    AddressResultDto getById(Long id);
    AddressResultDto getByName(String name);
    void delete(Long id);
}
