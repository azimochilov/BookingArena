package com.booking.service.role;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    RoleResultDto getByName(String name);
    RoleResultDto getById(Long id);
    List<RoleResultDto> getAll();
    RoleResultDto update(Long id, RoleUpdateDto roleDto);
    RoleResultDto create(RoleCreationDto roleDto);
    void delete(Long id);

}
