package com.booking.domain.dtos.roles;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreationDto {
    private String name;
    private List<PrivilegeCreationDto> privileges;

}
