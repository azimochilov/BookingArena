package com.booking.service.role;

import com.booking.domain.dtos.roles.RolePrivilegeDto;
import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;
import com.booking.domain.entities.user.Privilege;
import com.booking.domain.entities.user.Role;
import com.booking.domain.entities.user.RolePrivilege;
import com.booking.exception.NotFoundException;
import com.booking.repository.privilege.PrivilegeRepository;
import com.booking.repository.role.RolePrivilegeRepository;
import com.booking.repository.role.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
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
        try {
            for (RolePrivilegeDto p : roleDto.getRolePrivileges()) {
                Privilege privilege = privilegeRepository.findByName(p.getPrivilege()).orElseThrow(() ->
                        new NotFoundException("Not found privilege with name: " + p.getPrivilege()));
                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setPrivilege(privilege);
                rolePrivilege.setRole(role);
            }
            role.setName(roleDto.getName());
            role.setCreatedAt(Instant.now());
            role.setUpdatedAt(Instant.now());
            roleRepository.save(role);
            return modelMapper.map(role,RoleResultDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Invalid role details " + e.getMessage());
        }
    }

    @Override
    public RoleResultDto addPrivilege(Long roleId, List<PrivilegeCreationDto> privileges) {
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + roleId));
        for (PrivilegeCreationDto privilegeDto : privileges) {
            Privilege privilege = privilegeRepository.findByName(privilegeDto.getName()).orElseThrow(() ->
                    new NotFoundException("Not found privilege with name: " + privilegeDto.getName()));
            if(!isPrivilegeExist(role.getRolePrivileges(), privilege)) {
                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setPrivilege(privilege);
                rolePrivilege.setRole(role);
                rolePrivilegeRepository.save(rolePrivilege);;
            }
        }
        roleRepository.save(role);
        return modelMapper.map(role, RoleResultDto.class);
    }

    @Override
    public void delete(Long id)
    {

        roleRepository.deleteById(id);
    }

    private boolean isPrivilegeExist(List<RolePrivilege> privilegesInRole, Privilege privilege) {
        for (RolePrivilege rolePrivilege : privilegesInRole) {
            if (rolePrivilege.getPrivilege().getName().equals(privilege.getName())) {
                return true;
            }
        }
        return false;
    }

}
