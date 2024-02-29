package com.booking.api.arena;

import com.booking.domain.dtos.arena.ArenaCreationDto;
import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.arena.ArenaUpdateDto;
import com.booking.service.arena.ArenaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/arenas")
@RequiredArgsConstructor
public class ArenaController {

    private final ArenaService arenaService;

    @GetMapping("/{id}")
    public ResponseEntity<ArenaResultDto> getArenaById(@PathVariable Long id) {
        ArenaResultDto arenaResultDto = arenaService.getById(id);
        return ResponseEntity.ok(arenaResultDto);
    }

    @GetMapping
    public ResponseEntity<List<ArenaResultDto>> getAllArenas() {
        List<ArenaResultDto> arenas = arenaService.getAll();
        return ResponseEntity.ok(arenas);
    }

    @PostMapping
    public ResponseEntity<ArenaResultDto> createArena(@RequestBody ArenaCreationDto arenaCreationDto,
                                                      @RequestParam("file") MultipartFile file) {
        ArenaResultDto arenaResultDto = arenaService.create(arenaCreationDto, file);
        return new ResponseEntity<>(arenaResultDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArenaResultDto> updateArena(@PathVariable Long id,
                                                      @RequestBody ArenaUpdateDto arenaDto,
                                                      @RequestParam("file") MultipartFile file) {
        ArenaResultDto updatedArena = arenaService.update(id, arenaDto, file);
        return ResponseEntity.ok(updatedArena);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArena(@PathVariable Long id) {
        arenaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
