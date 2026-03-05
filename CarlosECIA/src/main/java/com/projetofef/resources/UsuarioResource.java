package com.projetofef.resources;

import com.projetofef.domains.dtos.UsuarioDTO;
import com.projetofef.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
    private final UsuarioService service;
    public UsuarioResource(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> list(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        List<UsuarioDTO> all = service.findAll();
        Page<UsuarioDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        UsuarioDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Validated(UsuarioDTO.Create.class) UsuarioDTO dto) {
        UsuarioDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(UsuarioDTO.Update.class) UsuarioDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
