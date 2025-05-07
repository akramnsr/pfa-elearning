package com.elearning.rest;

import com.elearning.dto.RoleDto;
import com.elearning.mapper.RoleMapper;
import com.elearning.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleService service;
    private final RoleMapper mapper;

    public RoleRestController(RoleService service,
                              RoleMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@Valid @RequestBody RoleDto dto) {
        var ent   = mapper.toEntity(dto);
        var saved = service.save(ent);
        var out   = mapper.toDto(saved);
        URI loc = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(out.getId())
                .toUri();
        return ResponseEntity.created(loc).body(out);
    }

    @GetMapping
    public ResponseEntity<Page<RoleDto>> list(Pageable p) {
        Page<RoleDto> page = service.findAll(p)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        RoleDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rôle introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto dto
    ) {
        dto.setId(id);
        var upd = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDto(upd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
