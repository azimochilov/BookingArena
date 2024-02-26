package com.booking.service.arena.info;

import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;

import java.util.Optional;

public class ArenaInfoServiceImpl implements ArenaInfoService{
    @Override
    public Optional<ArenaInfoResultDto> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ArenaInfoResultDto> create(ArenaInfoCreationDto arenaInfoDto) {
        return Optional.empty();
    }

    @Override
    public Optional<ArenaInfoResultDto> update(Long id, ArenaInfoCreationDto arenaInfoDto) {
        return Optional.empty();
    }
}
