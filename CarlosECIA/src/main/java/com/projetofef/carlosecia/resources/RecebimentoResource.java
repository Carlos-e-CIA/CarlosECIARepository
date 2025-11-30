package com.projetofef.resources;

import com.projetofef.carlosecia.domains.dtos.RecebimentoDTO;
import com.projetofef.carlosecia.services.RecebimentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recebimentos")
public class RecebimentoResource {
    private final RecebimentoService service;

    public RecebimentoResource(RecebimentoService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<Page<RecebimentoDTO>> list(
            @RequestParam(required = false) Integer lancamentoId,
            @RequestParam(required = false) Integer contaBancariaId,
            @PageableDefault(size = 20, sort = "dataRecebimento") Pageable pageable)

    {
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
            @RequestParam(required = false) Integer contaBancariaId)

    {
        List<RecebimentoDTO> list;

        if (lancamentoId != null && contaBancariaId != null) {
            list = service.findAllByLancamentoAndContaBancaria(lancamentoId, contaBancariaId);
        } else if (lancamentoId != null) {
            list = service.findAllByLancamento(lancamentoId);
        } else if (contaBancariaId != null) {
            list = service.findAllByContaBancaria(contaBancariaId);
        } else {
            list = service.findAll();
        }

        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecebimentoDTO> findById(@PathVariable Integer id) {
        RecebimentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/observacao/{obs}")
    public ResponseEntity<RecebimentoDTO> findByObservacao(@PathVariable String obs) {
        RecebimentoDTO dto = service.findByObservacao(obs);
        return ResponseEntity.ok(dto);
    }
}
