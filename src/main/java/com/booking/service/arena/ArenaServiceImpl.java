package com.booking.service.arena;

import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public class ArenaServiceImpl implements ArenaService{

    @Override
    public Optional<ArenaResultDto> getById(Long id) {
        return null;
    }

    @Override
    public List<ArenaResultDto> getAll() {
        return null;
    }

    @Override
    public List<ArenaResultDto> getByFilter(FiltersDto filters) {
        return null;
    }

    @Override
    public Optional<ArenaResultDto> create(String arenaDto, MultipartFile file) {
        return Optional.empty();
    }

    @Override
    public Optional<ArenaResultDto> update(Long id, String arenaDto, MultipartFile file) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
