package com.booking.service.arena.info;

import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
import com.booking.domain.entities.address.Address;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaInfoRepository;
import com.booking.service.address.AddressService;
import com.booking.utils.TimeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

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

    @Override
    public ArenaInfoResultDto createWithDto(ArenaInfoCreationDto arenaInfoCreationDto) {
        addressService.create(arenaInfoCreationDto.getAddress());


        ArenaInfo arenaInfo = modelMapper.map(arenaInfoCreationDto,ArenaInfo.class);
        arenaInfo.setCreatedAt(Instant.now());

        arenaInfoRepository.save(arenaInfo);

        return modelMapper.map(arenaInfoCreationDto, ArenaInfoResultDto.class);
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
