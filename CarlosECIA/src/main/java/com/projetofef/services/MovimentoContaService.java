package com.projetofef.services;

import com.projetofef.domains.MovimentoConta;
import com.projetofef.domains.dtos.MovimentoContaDTO;
import com.projetofef.domains.dtos.ContaBancariaDTO;
import com.projetofef.mappers.MovimentoContaMapper;
import com.projetofef.mappers.ContaBancariaMapper;
import com.projetofef.repositories.MovimentoContaRepository;
import com.projetofef.repositories.ContaBancariaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import java.util.List;

@Service
public class MovimentoContaService {
    private static final int MAX_PAGE_SIZE = 200;

    private final MovimentoContaRepository movimentoContaRepo;
    private final ContaBancariaRepository contaBancariaRepo;

    public MovimentoContaService(MovimentoContaRepository movimentoContaRepo, ContaBancariaRepository contaBancariaRepo) {
        this.movimentoContaRepo = movimentoContaRepo;
        this.contaBancariaRepo = contaBancariaRepo;
    }

    @Transactional(readOnly = true)
    public List<MovimentoContaDTO> findAll() {
        return MovimentoContaMapper.toDtoList(movimentoContaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<MovimentoContaDTO> findAll(Pageable pageable) {
        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        }
        else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<MovimentoConta> page = movimentoContaRepo.findAll(effective);
        return MovimentoContaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<MovimentoContaDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaBancariaId é obrigatório");
        }

        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + "não encontrado");
        }

        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        }
        else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<MovimentoConta> page = movimentoContaRepo.findByContaBancaria_Id(contaBancariaId, effective);
        return MovimentoContaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<MovimentoContaDTO> findAllByContaBancaria(Integer contaBancariaId) {
        return findAllByContaBancaria(contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public MovimentoContaDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de MovimentoConta é obrigatório");
        }

        return movimentoContaRepo.findById(id)
                .map(MovimentoContaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("MovimentoConta não encontrado: id=" + id));
    }

    @Transactional(readOnly = true)
    public MovimentoContaDTO findByHistorico(String historico) {
        if (historico == null || historico.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Historico do MovimentoConta é obrigatório");
        }

        String normalizedHistorico = historico.trim();

        return movimentoContaRepo.findByHistorico(normalizedHistorico)
                .map(MovimentoContaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("MovimentoConta não encontrado: Historico=" + normalizedHistorico));
    }
}
