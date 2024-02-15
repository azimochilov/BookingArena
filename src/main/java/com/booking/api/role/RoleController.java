package com.booking.api.role;


import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;
import com.booking.domain.entities.Role;
import com.booking.domain.entities.RolePrivilege;
import com.booking.exception.NotFoundException;
import com.booking.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void create(@RequestBody RoleCreationDto roleDto) {

        roleService.create(roleDto);
    }

    @GetMapping
    public List<RoleResultDto> getAll() {
        return roleService.getAll();
    }


    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RoleUpdateDto roleDto) {
        roleService.update(id, roleDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}

