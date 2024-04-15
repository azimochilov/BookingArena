package com.booking.service.arena;

import com.booking.domain.dtos.arena.ArenaCreationDto;
import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.arena.ArenaUpdateDto;
import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.filter.FiltersDto;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.arena.ArenaInfo;
import com.booking.domain.entities.user.User;
import com.booking.exception.NotFoundException;
import com.booking.repository.arena.ArenaRepository;
import com.booking.repository.user.UserRepository;
import com.booking.service.arena.info.ArenaInfoService;
import com.booking.service.filesystem.ImageService;
import com.booking.utils.SecurityUtils;
import com.booking.utils.TimeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository arenaRepository;
    private final ArenaInfoService arenaInfoService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ArenaResultDto getById(Long id) {

        Arena arena = arenaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("not found with given id")
        );

        return modelMapper.map(arena, ArenaResultDto.class);
    }

    @Override
    public List<ArenaResultDto> getAll() {

        List<Arena> arenas = arenaRepository.findAll();
        List<ArenaResultDto> arenaResultDTOs = arenas.stream()
                .map(arena -> modelMapper.map(arena, ArenaResultDto.class))
                .collect(Collectors.toList());
        return arenaResultDTOs;
    }

    @Override
    public ArenaResultDto create(ArenaCreationDto arenaCreationDto) {

        Arena arena = new Arena();
        arena.setArenaInfo(modelMapper.map(arenaCreationDto.getArenaInfo(), ArenaInfo.class));
        arena.setName(arenaCreationDto.getName());
        arena.setDescription(arenaCreationDto.getDescription());
        arena.setStatus(true);

        User user = userRepository.findById(Objects.requireNonNull(SecurityUtils.getCurrentUserId())).orElseThrow(
                () -> new NotFoundException("User is Not valid")
        );
        arena.setUser(user);

        ArenaInfo arenaInfo = arena.getArenaInfo();
        arenaInfo.setWorkedFrom(TimeMapper.convertLocalTimeToInstant(arenaCreationDto.getArenaInfo().getWorkedFrom()));
        arenaInfo.setWorkedTo(TimeMapper.convertLocalTimeToInstant(arenaCreationDto.getArenaInfo().getWorkedTo()));
        arenaInfo.setCity(arenaCreationDto.getArenaInfo().getCity());
        arenaInfo.setLatitude(arenaCreationDto.getArenaInfo().getLatitude());
        arenaInfo.setLongitude(arenaCreationDto.getArenaInfo().getLongitude());
        arenaInfo.setArena(arena);
        arenaInfoService.create(arenaInfo);

        arenaRepository.save(arena);

        ArenaResultDto arenaResultDto = modelMapper.map(arenaCreationDto, ArenaResultDto.class);
        arenaResultDto.getArenaInfo().setWorkedFrom(arenaCreationDto.getArenaInfo().getWorkedFrom());
        arenaResultDto.getArenaInfo().setWorkedTo(arenaCreationDto.getArenaInfo().getWorkedTo());

        return arenaResultDto;
    }

    @Override
    public ArenaResultDto update(Long id, ArenaUpdateDto arenaDto, MultipartFile file) {
        Arena arena = arenaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("with given id not found")
        );
        arena.setName(arenaDto.getName());
        arena.setDescription(arenaDto.getDescription());

        ArenaInfoResultDto arenaInfo = arenaInfoService.update(arena.getArenaInfo().getId(), arenaDto.getArenaInfo());
        arena.setArenaInfo(modelMapper.map(arenaInfo, ArenaInfo.class));
        arenaRepository.save(arena);
        return modelMapper.map(arena, ArenaResultDto.class);
    }

    public List<ArenaResultDto> getByFilter(FiltersDto filters) {
        Sort sort = filters.getDirection().equalsIgnoreCase("asc") ?
                Sort.by("distance").ascending() :
                Sort.by("distance").descending();
        List<Arena> filtiredArenas = arenaRepository
                .findFilteredAndSortedArenas(filters.getCity(), filters.getLatitude(), filters.getLongitude(), filters.getFrom(), filters.getTo(), sort);
        List<ArenaResultDto> arenaResultDTOs = filtiredArenas.stream()
                .map(arena -> modelMapper.map(arena, ArenaResultDto.class))
                .collect(Collectors.toList());
        return arenaResultDTOs;
    }

    @Override
    public void delete(Long id) {
        Arena arena = arenaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("not found with given id")
        );
        arenaRepository.delete(arena);
    }

}