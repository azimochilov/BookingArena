package com.booking.service.privilege;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeResultDto;

import java.util.List;

public interface IPrivilegeServicec {
    PrivilegeResultDto create(PrivilegeCreationDto privilegeDto);
    PrivilegeResultDto getById(Long id);
    PrivilegeResultDto update(Long id, PrivilegeCreationDto privilegeDto);
    List<PrivilegeResultDto> getAll();
    void delete(Long id);
}
