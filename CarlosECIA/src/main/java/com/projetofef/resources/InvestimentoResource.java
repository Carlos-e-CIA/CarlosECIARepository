package com.projetofef.resources;

import com.projetofef.domains.dtos.InvestimentoDTO;
import com.projetofef.services.InvestimentoService;
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
@RequestMapping("/api/investimento")
public class InvestimentoResource {
    private final InvestimentoService service;

    public InvestimentoResource(InvestimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<InvestimentoDTO>> list(
            @RequestParam(required = false) Integer contaBancariaId,
            @PageableDefault(size = 20, sort = "codigo") Pageable pageable) {

        Page<InvestimentoDTO> page;

        if (contaBancariaId != null) {
            page = service.findAllByContaBancaria(contaBancariaId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvestimentoDTO>> listAll(
            @RequestParam(required = false) Integer contaBancariaId) {

        List<InvestimentoDTO> body;

        if (contaBancariaId != null) {
            body = service.findAllByContaBancaria(contaBancariaId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestimentoDTO> findById(@PathVariable Integer id) {
        InvestimentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<InvestimentoDTO> findByCodigo(@PathVariable String codigo) {
        InvestimentoDTO dto = service.findByCodigo(codigo);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestimentoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(InvestimentoDTO.Update.class) InvestimentoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<InvestimentoDTO> create(
            @RequestBody @Validated(InvestimentoDTO.Create.class) InvestimentoDTO dto) {
        InvestimentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}