package com.booking.service.arena;

import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArenaService {
    Optional<ArenaResultDto> getById(Long id);
    List<ArenaResultDto> getAll();
    List<ArenaResultDto> getByFilter(FiltersDto filters);
    Optional<ArenaResultDto> create(String arenaDto, MultipartFile file);
    Optional<ArenaResultDto> update(Long id, String arenaDto, MultipartFile file);
    void delete(Long id);
}
