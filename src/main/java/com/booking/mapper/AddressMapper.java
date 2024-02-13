package com.booking.mapper;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.Address;
import org.mapstruct.factory.Mappers;

public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressUpdateDtoToAddress(AddressUpdateDto addressUpdateDto);
    Address addressCreationDtoToAddress(AddressCreationDto addressCreationDto);
}
