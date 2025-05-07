package com.elearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FormationDto {
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 100, message = "Le titre ne peut dépasser 100 caractères")
    private String title;

    @NotBlank(message = "La description est obligatoire")
    private String description;


    public FormationDto() {}
    public FormationDto(Long id, String title, String description) {
        this.id = id; this.title = title; this.description = description;
    }

    public FormationDto(Long id, String title, String description, String unused) {
        this.id          = id;
        this.title       = title;
        this.description = description;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
