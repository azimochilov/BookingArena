package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import com.booking.domain.entities.address.Address;
import com.booking.exception.NotFoundException;
import com.booking.repository.address.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Override
    public AddressResultDto create(AddressCreationDto addressDto) {

        Address address = modelMapper.map(addressDto, Address.class);
        addressRepository.save(address);

        return modelMapper.map(address, AddressResultDto.class);
    }



    @Override
    public Address createForUser(AddressCreationDto addressCreationDto) {
        Address address = modelMapper.map(addressCreationDto,Address.class);
        return addressRepository.save(address);
    }

    @Override
    public Address createForArena(Address address) {
        addressRepository.save(address);
        return address;
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

        return modelMapper.map(existingAddress, AddressResultDto.class);
    }

    @Override
    public AddressResultDto getById(Long id) {

        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Address not found")
                );

        return modelMapper.map(existingAddress, AddressResultDto.class);
    }

    @Override
    public AddressResultDto getByName(String name) {
        Address existingAddress = addressRepository.findByCity(name)
                .orElseThrow(
                        () -> new NotFoundException("Address not found")
                );

        return modelMapper.map(existingAddress, AddressResultDto.class);
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
