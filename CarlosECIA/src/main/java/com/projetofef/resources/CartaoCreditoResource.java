package com.projetofef.resources;

import com.projetofef.domains.dtos.CartaoCreditoDTO;
import com.projetofef.services.CartaoCreditoService;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/cartaoCredito")
public class CartaoCreditoResource {
    private final CartaoCreditoService service;

    public CartaoCreditoResource(CartaoCreditoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CartaoCreditoDTO>> list(
            @RequestParam(required = false) Integer usuarioId,
            @PageableDefault(size = 20, sort = "fechamentoFaturaDia") Pageable pageable) {

        Page<CartaoCreditoDTO> page;

        if (usuarioId != null) {
            page = service.findAllByUsuario(usuarioId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartaoCreditoDTO>> listAll(
            @RequestParam(required = false) Integer usuarioId) {

        List<CartaoCreditoDTO> body;

        if (usuarioId != null) {
            body = service.findAllByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoCreditoDTO> findById(@PathVariable Integer id) {
        CartaoCreditoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/apelido/{apelido}")
    public ResponseEntity<CartaoCreditoDTO> findByApelido(@PathVariable String apelido) {
        CartaoCreditoDTO dto = service.findByApelido(apelido);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaoCreditoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(CartaoCreditoDTO.Update.class) CartaoCreditoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<CartaoCreditoDTO> create(
            @RequestBody @Validated(CartaoCreditoDTO.Create.class) CartaoCreditoDTO dto) {
        CartaoCreditoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}