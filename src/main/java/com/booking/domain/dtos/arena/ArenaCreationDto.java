package com.booking.domain.dtos.arena;

import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArenaCreationDto {

    private String name;
    private String description;
    private boolean status = true;
    private ArenaInfoCreationDto arenaInfo;
    @JsonIgnore
    private MultipartFile imageFile;
    private String image;
}
