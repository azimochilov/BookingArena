package com.booking.service.arena;

import com.booking.domain.dtos.arena.ArenaCreationDto;
import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArenaService {
    ArenaResultDto getById(Long id);
    List<ArenaResultDto> getAll();
    List<ArenaResultDto> getByFilter(FiltersDto filters);
    ArenaResultDto create(ArenaCreationDto arenaCreationDto, MultipartFile file);
    ArenaResultDto update(Long id, ArenaCreationDto arenaCreationDto, MultipartFile file);
    void delete(Long id);
}
