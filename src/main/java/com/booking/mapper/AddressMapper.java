package com.booking.mapper;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressUpdateDtoToAddress(AddressUpdateDto addressUpdateDto);
    Address addressCreationDtoToAddress(AddressCreationDto addressCreationDto);
    AddressResultDto addressToAddressResultDto(Address address);
    AddressResultDto addressCreationDtoToAddressResult(AddressCreationDto addressCreationDto);
    List<AddressResultDto> usersToUserDtoList(List<Address> addresses);
}
