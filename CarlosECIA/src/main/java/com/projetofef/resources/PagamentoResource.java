package com.projetofef.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.projetofef.carlosecia.domains.dtos.PagamentoDTO;
import com.projetofef.carlosecia.domains.Pagamento;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/pagamento")
public class PagamentoResource {
    private final PagamentoService service;

    public PagamentoResource(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>> list(
            @RequestParam(required = false) Integer contaOrigem,
            @RequestParam(required = false) Integer lancamento,
            @PageableDefault(size = 20, sort = "contaOrigem") Pageable pageable) {

        Page<PagamentoDTO> page;

        if (contaOrigem != null && lancamento != null) {
            page = service.findAllByContaBancariaAndLancamento(contaOrigem, lancamento, pageable);
        } else if (contaOrigem != null) {
            page = service.findAllByContaBancaria(contaOrigem, pageable);
        } else if (lancamento != null) {
            page = service.findAllByLancamento(lancamento, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PagamentoDTO>> listAll(
            @RequestParam(required = false) Integer contaOrigem,
            @RequestParam(required = false) Integer lancamento) {

        List<PagamentoDTO> body;

        if (contaOrigem != null && lancamento != null) {
            body = service.findAllByContaBancariaAndLancamento(contaOrigem, lancamento);
        } else if (contaOrigem != null) {
            body = service.findAllByContaBancaria(contaOrigem);
        } else if (lancamento != null) {
            body = service.findAllByLancamento(lancamento);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Integer id) {
        PagamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(PagamentoDTO.Update.class) PagamentoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> create(
            @RequestBody @Validated(PagamentoDTO.Create.class) PagamentoDTO dto) {
        PagamentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
