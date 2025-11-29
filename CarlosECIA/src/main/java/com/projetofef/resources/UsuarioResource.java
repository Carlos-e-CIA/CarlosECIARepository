package com.projetofef.resources;
import com.projetofef.domains.dtos.UsuarioDTO;
import com.projetofef.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {

    private final UsuarioService service;

    public UsuarioResource(UsuarioService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> list(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        Page<UsuarioDTO> page;

        if (nome != null && !nome.isBlank()) {
            page = service.findAllByNome(nome.trim(), pageable);
        } else {
            page = service.findAll(pageable);
        }
        return ResponseEntity.ok(page);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> listAll(
            @RequestParam(required = false) String nome) {

        List<UsuarioDTO> list;

        if (nome != null && !nome.isBlank()) {
            list = service.findAllByNome(nome.trim());
        } else {
            list = service.findAll();
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(UsuarioDTO.Update.class) UsuarioDTO dto) {

        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(
            @RequestBody @Validated(UsuarioDTO.Create.class) UsuarioDTO dto) {
        UsuarioDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

}

