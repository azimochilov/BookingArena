package com.booking.service.role;

import com.booking.domain.dtos.roles.RoleCreationDto;
import com.booking.domain.dtos.roles.RoleResultDto;
import com.booking.domain.dtos.roles.RoleUpdateDto;
import com.booking.domain.entities.user.Role;
import com.booking.repository.privilege.PrivilegeRepository;
import com.booking.repository.role.RolePrivilegeRepository;
import com.booking.repository.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import org.modelmapper.config.Configuration;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @Mock
    private RolePrivilegeRepository rolePrivilegeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleService roleService;
    @Mock
    private Configuration configuration;

    private Role role;
    private RoleResultDto roleResultDto;
    private RoleCreationDto roleCreationDto;
    private RoleUpdateDto roleUpdateDto;

    @BeforeEach
    public void setUp() {

        role = new Role(1L, "Admin", new ArrayList<>(), Instant.now(), Instant.now());
        roleResultDto = new RoleResultDto("Admin", Collections.emptyList());
        roleCreationDto = new RoleCreationDto();
        roleCreationDto.setName("Admin");
        roleCreationDto.setRolePrivileges(Collections.emptyList());
        roleUpdateDto = new RoleUpdateDto("UpdatedName", Collections.emptyList());
        when(modelMapper.getConfiguration()).thenReturn(configuration);

        lenient().when(modelMapper.map(any(Role.class), eq(RoleResultDto.class))).thenReturn(roleResultDto);
        lenient().when(roleRepository.save(any(Role.class))).thenReturn(role);
    }

    @Test
    public void whenCreateRole_thenRoleIsCreated() {
        RoleResultDto createdRole = roleService.create(roleCreationDto);

        assertEquals(roleResultDto.getName(), createdRole.getName());
    }

    @Test
    public void whenGetById_thenRoleIsReturned() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        RoleResultDto foundRole = roleService.getById(1L);

        assertEquals(roleResultDto.getName(), foundRole.getName());
    }

    @Test
    public void whenGetByName_thenRoleIsReturned() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        RoleResultDto foundRole = roleService.getByName("Admin");

        assertEquals(roleResultDto.getName(), foundRole.getName());
    }

    @Test
    public void whenGetAll_thenAllRolesAreReturned() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        List<RoleResultDto> roles = roleService.getAll();

        assertFalse(roles.isEmpty());
        assertEquals(roleResultDto.getName(), roles.get(0).getName());
    }

    @Test
    public void whenUpdateRole_thenRoleIsUpdated() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        RoleResultDto updatedRole = roleService.update(1L, roleUpdateDto);

        assertEquals(roleResultDto.getName(), updatedRole.getName());

        verify(configuration).setSkipNullEnabled(false);
    }


    @Test
    public void whenDeleteRole_thenRoleIsDeleted() {
        Long roleId = 1L;
        doNothing().when(roleRepository).deleteById(roleId);

        assertDoesNotThrow(() -> roleService.delete(roleId));

        verify(roleRepository, times(1)).deleteById(roleId);
    }


}

