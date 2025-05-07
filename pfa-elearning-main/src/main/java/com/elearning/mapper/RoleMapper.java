// src/main/java/com/elearning/mapper/RoleMapper.java
package com.elearning.mapper;

import com.elearning.dto.RoleDto;
import com.elearning.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role r) {
        return new RoleDto(
                r.getId(),
                r.getNom()
        );
    }

    public Role toEntity(RoleDto dto) {
        Role r = new Role();
        r.setId(dto.getId());
        r.setNom(dto.getName());
        return r;
    }
}
