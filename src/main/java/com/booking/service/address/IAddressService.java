package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;

public interface IAddressService {
    AddressResultDto create(AddressCreationDto addressDto);
    AddressResultDto update(Long id, AddressUpdateDto addressDto);
    AddressResultDto getById(Long id);
    void delete(Long id);
}
