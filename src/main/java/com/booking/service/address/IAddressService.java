package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;

public interface IAddressService {
    AddressResultDto create(AddressCreationDto addressDto);
    AddressResultDto update(Long id, AddressCreationDto addressDto);
    AddressResultDto getById(Long id);
    void delete(Long id);
}
