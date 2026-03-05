package com.projetofef.resources;

import com.projetofef.domains.MovimentoConta;
import com.projetofef.domains.dtos.MovimentoContaDTO;
import com.projetofef.domains.dtos.ContaBancariaDTO;
import com.projetofef.services.MovimentoContaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contaBancaria")
public class MovimentoContaResource {
    private final MovimentoContaService service;

    public MovimentoContaResource(MovimentoContaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<MovimentoContaDTO>> list(
            @RequestParam(required = false) Integer contaBancariaId,
            @RequestParam(required = false) Integer investimentoId,
            @PageableDefault(size = 20, sort = "historico") Pageable pageable) {

        Page<MovimentoContaDTO> page;

        if (contaBancariaId != null && investimentoId != null) {
            page = service.findAllByContaBancariaAndInvestimento(contaBancariaId, investimentoId, pageable);
        } else if (contaBancariaId != null) {
            page = service.findAllByContaBancaria(contaBancariaId, pageable);
        } else if (investimentoId != null) {
            page = service.findAllByInvestimento(investimentoId, pageable);
        } else {
            page = service.findAll(pageable);
        }
        
        return ResponseEntity.ok(page);
    }
    @GetMapping("/all")
    public ResponseEntity<List<MovimentoContaDTO>> listAll(
            @RequestParam(required = false) Integer contaBancariaId,
            @RequestParam(required = false) Integer investimentoId){

        List<MovimentoContaDTO> body;

        if (contaBancariaId != null && investimentoId != null) {
            body = service.findAllByContaBancariaAndInvestimento(contaBancariaId, investimentoId);
        } else if (contaBancariaId != null) {
            body = service.findAllByContaBancaria(contaBancariaId);
        } else if (investimentoId != null) {
            body = service.findAllByInvestimento(investimentoId);
        } else {
            body = service.findAll();
        }
        
        return ResponseEntity.ok(body);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovimentoContaDTO> findById(@PathVariable Integer id) {
        MovimentoContaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/historico/{historico}")
    public ResponseEntity<MovimentoContaDTO> findByHistorico(@PathVariable String historico) {
        MovimentoContaDTO dto = service.findByHistorico(historico);
        return ResponseEntity.ok(dto);
    }
}