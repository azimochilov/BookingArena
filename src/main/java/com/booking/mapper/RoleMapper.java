package com.booking.mapper;

import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.entities.Role;
import com.booking.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResultDto roleToResultDto(Role role);
    List<RoleResultDto>  rolesToResultRoles(List<Role> roles);
}
