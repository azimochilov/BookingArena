package com.booking.service.privilege;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.privileges.PrivilegeResultDto;
import com.booking.domain.entities.Address;
import com.booking.domain.entities.Privilege;
import com.booking.exception.NotFoundException;
import com.booking.mapper.PrivilegeMapper;
import com.booking.repository.PrivilegeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PrivilegeService implements IPrivilegeServicec{
    private final PrivilegeRepository privilegeRepository;
    @Override
    public PrivilegeResultDto create(PrivilegeCreationDto privilegeDto) {
        Privilege privilege = PrivilegeMapper.INSTANCE.privilegeCreationToPrivilege(privilegeDto);

        return PrivilegeMapper.INSTANCE.privilegeToPrivilegeResult(privilege);
    }

    @Override
    public PrivilegeResultDto getById(Long id) {

        Privilege privilege = privilegeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("not found with given id")
        );

        return PrivilegeMapper.INSTANCE.privilegeToPrivilegeResult(privilege);
    }

    @Override
    public PrivilegeResultDto update(Long id, PrivilegeCreationDto privilegeDto)
    {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found privilege with id: " + id));

        privilege.setName(privilegeDto.getName());
        privilege.setUpdatedAt(Instant.now());

        privilegeRepository.save(privilege);

        return PrivilegeMapper.INSTANCE.privilegeToPrivilegeResult(privilege);
    }

    @Override
    public List<PrivilegeResultDto> getAll() {

        List<Privilege> privileges = privilegeRepository.findAll();

        return PrivilegeMapper.INSTANCE.privilegesToResultPrivileges(privileges);
    }

    @Override
    public void delete(Long id) {
        Privilege privilegeForDeletion = privilegeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Privilege not found")
                );
        privilegeRepository.delete(privilegeForDeletion);
    }

}
