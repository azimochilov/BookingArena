package com.booking.service.arena.info;

import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaInfoRepository;
import com.booking.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArenaInfoServiceImpl implements ArenaInfoService{
    private final ArenaInfoRepository arenaInfoRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Override
    public ArenaInfoResultDto getById(Long id) {
        ArenaInfo arenaInfo = arenaInfoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found with given id")
        );

        return modelMapper.map(arenaInfo,ArenaInfoResultDto.class);
    }
    @Override
    public ArenaInfoResultDto create(ArenaInfo arenaInfo) {
        addressService.createForArena(arenaInfo.getAddress());
        arenaInfo.setCreatedAt(Instant.now());
        arenaInfoRepository.save(arenaInfo);


        return modelMapper.map(arenaInfo,ArenaInfoResultDto.class);
    }

    public ArenaInfoResultDto createWithDto(ArenaInfoCreationDto arenaInfoCreationDto) {
        // Assuming AddressService.create(...) handles the address part correctly
        AddressResultDto addressResult = addressService.create(arenaInfoCreationDto.getAddress());
        // Map the creation DTO to entity
        ArenaInfo arenaInfo = modelMapper.map(arenaInfoCreationDto, ArenaInfo.class);
        // Set additional properties as necessary
        arenaInfo.setCreatedAt(Instant.now());
        // Save the entity
        arenaInfo = arenaInfoRepository.save(arenaInfo);
        // Map the saved entity to result DTO
        ArenaInfoResultDto resultDto = modelMapper.map(arenaInfo, ArenaInfoResultDto.class);
        // Return the mapped result DTO
        return resultDto;
    }


    @Override
    public ArenaInfoResultDto update(Long id, ArenaInfoUpdateDto arenaInfoUpdateDto) {
        ArenaInfo arenaInfo = arenaInfoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found arena with id: " + id));

        AddressResultDto updatedAddress = addressService.update(arenaInfo.getAddress().getAddressId(), arenaInfoUpdateDto.getAddress());


        arenaInfoUpdateDto.setUpdatedAt(Instant.now());

        modelMapper.map(arenaInfoUpdateDto, arenaInfo);
        arenaInfoRepository.save(arenaInfo);

        return modelMapper.map(arenaInfo, ArenaInfoResultDto.class);
    }

}
