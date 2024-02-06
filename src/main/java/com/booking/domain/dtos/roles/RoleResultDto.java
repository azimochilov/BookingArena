package com.booking.domain.dtos.roles;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResultDto {
    private String name;
    private List<PrivilegeResultDto> privileges;

}
