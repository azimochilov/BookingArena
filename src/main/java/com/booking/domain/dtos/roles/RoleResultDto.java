package com.booking.domain.dtos.roles;

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
    private List<RolePrivilegeDto> rolePrivileges;
}
