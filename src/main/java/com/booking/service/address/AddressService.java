package com.booking.service.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AddressService implements IAddressService{
    @Override
    public AddressResultDto create(AddressCreationDto addressDto) {
        return null;
    }

    @Override
    public AddressResultDto update(Long id, AddressCreationDto addressDto) {
        return null;
    }

    @Override
    public AddressResultDto getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
