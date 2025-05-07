// src/main/java/com/elearning/dto/RoleDto.java
package com.elearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDto {
    private Long id;

    @NotBlank(message = "Le nom du rôle est obligatoire")
    @Size(max = 50, message = "Le nom du rôle ne peut dépasser 50 caractères")
    private String name;

    public RoleDto() {}

    public RoleDto(Long id, String name) {
        this.id   = id;
        this.name = name;
    }

    public RoleDto(Integer id, String nom) {
    }

    public Integer getId() { return Math.toIntExact(id); }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
