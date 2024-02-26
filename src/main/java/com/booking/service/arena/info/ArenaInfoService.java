package com.booking.service.arena.info;

import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;

import java.util.Optional;

public interface ArenaInfoService {
    Optional<ArenaInfoResultDto> getById(Long id);
    Optional<ArenaInfoResultDto> create(ArenaInfoCreationDto arenaInfoDto);
    Optional<ArenaInfoResultDto> update(Long id, ArenaInfoCreationDto arenaInfoDto);
}
