package com.booking.service.role;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;
import com.booking.domain.entities.Privilege;
import com.booking.domain.entities.Role;
import com.booking.domain.entities.RolePrivilege;
import com.booking.exception.NotFoundException;
import com.booking.mapper.RoleMapper;
import com.booking.repository.PrivilegeRepository;
import com.booking.repository.RoleRepository;
import com.booking.service.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
@Override
    public RoleResultDto getByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Not found role with name: " + name));

        return RoleMapper.INSTANCE.roleToResultDto(role);
    }
    @Override
    public RoleResultDto getById(Long id) {
        Role role  = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));
        return RoleMapper.INSTANCE.roleToResultDto(role);
    }
    @Override
    public List<RoleResultDto> getAll()
    {
        List<Role> roles = roleRepository.findAll();

        return RoleMapper.INSTANCE.rolesToResultRoles(roles);
    }

    @Override
    public RoleResultDto update(Long id, RoleUpdateDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));

        role.setName(roleDto.getName());

        roleRepository.save(role);

        return RoleMapper.INSTANCE.roleToResultDto(role);
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

        return RoleMapper.INSTANCE.roleToResultDto(role);
    }
    @Override
    public void delete(Long id)
    {

        roleRepository.deleteById(id);
    }

}
