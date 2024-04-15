package com.booking.domain.dtos.arena;

import com.booking.domain.dtos.arena.info.ArenaInfoResultDto;
import com.booking.domain.dtos.arena.info.ArenaInfoUpdateDto;
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
public class ArenaUpdateDto {
    private String name;
    private String description;
    @Builder.Default
    private boolean status = true;

    private ArenaInfoUpdateDto arenaInfo;
    @JsonIgnore
    private MultipartFile imageFile;
    private String image;
}
