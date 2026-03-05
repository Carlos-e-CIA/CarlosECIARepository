package com.projetofef.resources;

import com.projetofef.domains.dtos.RecebimentoDTO;
import com.projetofef.services.RecebimentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recebimento")
public class RecebimentoResource {
    private final RecebimentoService service;

    public RecebimentoResource(RecebimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RecebimentoDTO>> list(
            @RequestParam(required = false) Integer lancamentoId,
            @RequestParam(required = false) Integer contaBancariaId,
            @PageableDefault(size = 120, sort = "observacao") Pageable pageable) {

        Page<RecebimentoDTO> page;

        if (lancamentoId != null && contaBancariaId != null) {
            page = service.findAllByLancamentoAndContaBancaria(lancamentoId, contaBancariaId, pageable);
        } else if (lancamentoId != null) {
            page = service.findAllByLancamento(lancamentoId, pageable);
        } else if (contaBancariaId != null) {
            page = service.findAllByContaBancaria(contaBancariaId, pageable);
        } else {
            page = service.findAll(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecebimentoDTO>> listAll(
            @RequestParam(required = false) Integer lancamentoId,
            @RequestParam(required = false) Integer contaBancariaId) {

        List<RecebimentoDTO> body;

        if (lancamentoId != null && contaBancariaId != null) {
            body = service.findAllByLancamentoAndContaBancaria(lancamentoId, contaBancariaId);
        } else if (lancamentoId != null) {
            body = service.findAllByLancamento(lancamentoId);
        } else if (contaBancariaId != null) {
            body = service.findAllByContaBancaria(contaBancariaId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecebimentoDTO> findById(@PathVariable Integer id) {
        RecebimentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }
}