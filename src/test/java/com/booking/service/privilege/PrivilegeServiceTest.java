package com.booking.service.privilege;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeResultDto;
import com.booking.domain.entities.user.Privilege;
import com.booking.exception.NotFoundException;
import com.booking.repository.privilege.PrivilegeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PrivilegeServiceTest {

    @Mock
    private PrivilegeRepository privilegeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PrivilegeService privilegeService;

    private PrivilegeCreationDto privilegeCreationDto;
    private Privilege privilege;
    private PrivilegeResultDto privilegeResultDto;

    @BeforeEach
    public void setUp() {
        privilegeCreationDto = new PrivilegeCreationDto("Test Privilege");
        privilege = new Privilege();

        privilege.setId(1L);
        privilege.setName("Test Privilege");
        privilege.setCreatedAt(Instant.now());

        privilegeResultDto = new PrivilegeResultDto("Test Privilege");

        lenient().when(modelMapper.map(privilegeCreationDto, Privilege.class)).thenReturn(privilege);
        lenient().when(modelMapper.map(privilege, PrivilegeResultDto.class)).thenReturn(privilegeResultDto);
    }

    @Test
    public void whenCreatePrivilege_thenPrivilegeIsCreated() {
        when(privilegeRepository.save(any(Privilege.class))).thenReturn(privilege);
        when(modelMapper.map(privilegeCreationDto, Privilege.class)).thenReturn(privilege);
        when(modelMapper.map(privilege, PrivilegeResultDto.class)).thenReturn(privilegeResultDto);

        PrivilegeResultDto createdDto = privilegeService.create(privilegeCreationDto);

        assertNotNull(createdDto, "The created DTO should not be null");
        assertEquals(privilegeResultDto.getName(), createdDto.getName());
    }



    @Test
    public void whenGetById_thenPrivilegeIsReturned() {
        when(privilegeRepository.findById(anyLong())).thenReturn(Optional.of(privilege));

        PrivilegeResultDto foundDto = privilegeService.getById(1L);

        assertEquals(privilegeResultDto.getName(), foundDto.getName());
    }

    @Test
    public void whenUpdatePrivilege_thenPrivilegeIsUpdated() {
        when(privilegeRepository.findById(anyLong())).thenReturn(Optional.of(privilege));
        when(privilegeRepository.save(any(Privilege.class))).thenReturn(privilege);
        when(modelMapper.map(privilege, PrivilegeResultDto.class)).thenReturn(privilegeResultDto);

        PrivilegeResultDto updatedDto = privilegeService.update(1L, privilegeCreationDto);

        assertNotNull(updatedDto, "The updated DTO should not be null");
        assertEquals(privilegeResultDto.getName(), updatedDto.getName());
    }

    @Test
    public void whenGetAll_thenAllPrivilegesAreReturned() {
        when(privilegeRepository.findAll()).thenReturn(Collections.singletonList(privilege));
        when(modelMapper.map(any(Privilege.class), eq(PrivilegeResultDto.class))).thenReturn(privilegeResultDto);

        List<PrivilegeResultDto> dtoList = privilegeService.getAll();

        assertFalse(dtoList.isEmpty());
        assertEquals(privilegeResultDto.getName(), dtoList.get(0).getName());
    }

    @Test
    public void whenDeleteExistingPrivilege_thenNoExceptionThrown() {
        when(privilegeRepository.findById(anyLong())).thenReturn(Optional.of(privilege));
        doNothing().when(privilegeRepository).delete(any(Privilege.class));

        assertDoesNotThrow(() -> privilegeService.delete(1L));
        verify(privilegeRepository, times(1)).delete(privilege);
    }

    @Test
    public void whenDeleteNonExistingPrivilege_thenThrowNotFoundException() {
        when(privilegeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> privilegeService.delete(1L));
        assertTrue(exception.getMessage().contains("Privilege not found"));
    }
}
