package com.booking.domain.dtos.roles;

import com.booking.domain.dtos.RolePrivilegeDto;
import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleCreationDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    @JsonIgnore
    private Long id;
    private String name;
    private List<RolePrivilegeDto> rolePrivileges;

}
