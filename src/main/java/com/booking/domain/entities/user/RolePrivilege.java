package com.booking.domain.entities.user;

import com.booking.domain.dtos.privileges.PrivilegeCreationDto;
import com.booking.domain.dtos.roles.RoleCreationDto;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"privilege_id", "role_id"})})
public class RolePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public PrivilegeCreationDto getPrivilegeDto() {
        return PrivilegeCreationDto.builder()
                .name(privilege.getName())
                .build();
    }

    public RoleCreationDto getRole() {
        return RoleCreationDto.builder()
                .name(role.getName())
                .build();
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
        privilege.getRolePrivileges().add(this);
    }

    public void deletePrivilegeFromRole(Privilege privilege) {
        this.privilege = null;
        role.getRolePrivileges().remove(this);
    }

    public void setRole(Role role) {
        this.role = role;
        role.getRolePrivileges().add(this);
    }
}
