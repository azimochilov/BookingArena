package com.booking.domain.dtos.roles;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDto {
    private String name;
    private List<PrivilegeUpdateDto> privileges;
}
