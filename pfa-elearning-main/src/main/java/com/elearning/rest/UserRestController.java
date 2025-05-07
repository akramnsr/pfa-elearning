package com.elearning.rest;

import com.elearning.dto.UserDto;
import com.elearning.mapper.UserMapper;
import com.elearning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;
    private final UserMapper mapper;

    public UserRestController(UserService service,
                              UserMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
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
    public ResponseEntity<Page<UserDto>> list(Pageable p) {
        Page<UserDto> page = service.findAll(p)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
        UserDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Utilisateur introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto dto
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
