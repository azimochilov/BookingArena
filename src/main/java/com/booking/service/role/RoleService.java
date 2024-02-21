package com.booking.service.role;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;
import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.entities.Privilege;
import com.booking.domain.entities.Role;
import com.booking.domain.entities.RolePrivilege;
import com.booking.domain.entities.User;
import com.booking.exception.NotFoundException;
import com.booking.mapper.RoleMapper;
import com.booking.repository.PrivilegeRepository;
import com.booking.repository.RoleRepository;
import com.booking.service.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final ModelMapper modelMapper;
@Override
    public RoleResultDto getByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Not found role with name: " + name));

        return modelMapper.map(role, RoleResultDto.class);
    }
    @Override
    public RoleResultDto getById(Long id) {
        Role role  = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));
        return modelMapper.map(role, RoleResultDto.class);
    }
    @Override
    public List<RoleResultDto> getAll()
    {
        List<Role> roles = roleRepository.findAll();
        List<RoleResultDto> roleResultDTOs = roles.stream()
                .map(role -> modelMapper.map(role, RoleResultDto.class))
                .collect(Collectors.toList());

        return roleResultDTOs;
    }

    @Override
    public RoleResultDto update(Long id, RoleUpdateDto roleDto) {

        modelMapper.getConfiguration().setSkipNullEnabled(false);
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));

        modelMapper.map(roleDto,role);
        role.setName(roleDto.getName());

        roleRepository.save(role);

        return modelMapper.map(role, RoleResultDto.class);
    }

    @Override
    public RoleResultDto create(RoleCreationDto roleDto) {

        Role role = new Role();
        List<RolePrivilege> rolePrivileges = new ArrayList<>();
        for (PrivilegeCreationDto p : roleDto.getPrivileges()) {

            Privilege privilege = privilegeRepository.findByName(p.getName()).orElseThrow(() ->
                    new NotFoundException("Not found privilege with name: " + p.getName()));

            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setPrivilege(privilege);

            rolePrivilege.setRole(role);
        }

        role.setName(roleDto.getName());

        roleRepository.save(role);

        return modelMapper.map(roleDto, RoleResultDto.class);
    }
    @Override
    public void delete(Long id)
    {

        roleRepository.deleteById(id);
    }

}
