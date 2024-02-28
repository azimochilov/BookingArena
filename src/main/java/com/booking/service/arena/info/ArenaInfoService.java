package com.booking.service.arena.info;

import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
import com.booking.domain.entities.arena.ArenaInfo;

import java.util.Optional;

public interface ArenaInfoService {
    ArenaInfoResultDto getById(Long id);
    ArenaInfoResultDto create(ArenaInfo arenaInfo);
    ArenaInfoResultDto createWithDto(ArenaInfoCreationDto arenaInfoCreationDto);
    ArenaInfoResultDto update(Long id, ArenaInfoUpdateDto arenaInfoDto);

}
