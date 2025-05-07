package com.elearning.mapper;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import org.springframework.stereotype.Component;

@Component
public class FormationMapper {

    public FormationDto toDto(Formation f) {
        return new FormationDto(
                f.getId(),
                f.getTitre(),
                f.getDescription()
        );
    }

    public Formation toEntity(FormationDto dto) {
        Formation f = new Formation();
        f.setId(dto.getId());
        f.setTitre(dto.getTitle());
        f.setDescription(dto.getDescription());
        return f;
    }
}
