package com.booking.mapper;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeResultDto;
import com.booking.domain.dtos.privileges.PrivilegeUpdateDto;
import com.booking.domain.entities.user.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PrivilegeMapper {
    PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);

    Privilege privilegeCreationToPrivilege(PrivilegeCreationDto privilegeCreationDto);

    PrivilegeResultDto privilegeToPrivilegeResult(Privilege privilege);

    List<PrivilegeResultDto> privilegesToResultPrivileges(List<Privilege> privileges);

}
