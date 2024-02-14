package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.Address;
import com.booking.exception.NotFoundException;
import com.booking.mapper.AddressMapper;
import com.booking.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AddressService implements IAddressService{

    private final AddressRepository addressRepository;
    @Override
    public AddressResultDto create(AddressCreationDto addressDto) {

        Address address = AddressMapper.INSTANCE.addressCreationDtoToAddress(addressDto);
        addressRepository.save(address);

        return AddressMapper.INSTANCE.addressToAddressResultDto(address);
    }

    @Override
    public AddressResultDto update(Long id, AddressUpdateDto updtAddress) {
        Address existingAddress = addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Address not found")
        );

        existingAddress.setLongitude(updtAddress.getLongitude());
        existingAddress.setLatitude(updtAddress.getLatitude());
        existingAddress.setCity(updtAddress.getCity());
        existingAddress.setStreet(updtAddress.getStreet());

        addressRepository.save(existingAddress);

        return AddressMapper.INSTANCE.addressToAddressResultDto(existingAddress);
    }

    @Override
    public AddressResultDto getById(Long id) {

        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Address not found")
                );

        return AddressMapper.INSTANCE.addressToAddressResultDto(existingAddress);
    }

    @Override
    public void delete(Long id) {
        Address addressForDeletion = addressRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Address not found")
                );
        addressRepository.delete(addressForDeletion);
    }
}
