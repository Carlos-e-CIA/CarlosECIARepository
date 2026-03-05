package com.projetofef.services;

import com.projetofef.domains.Recebimento;
import com.projetofef.domains.dtos.RecebimentoDTO;
import com.projetofef.mappers.LancamentoMapper;
import com.projetofef.mappers.RecebimentoMapper;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.RecebimentoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecebimentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final RecebimentoRepository recebimentoRepo;
    private final LancamentoRepository lancamentoRepo;
    private final ContaBancariaRepository contaBancariaRepo;

    public RecebimentoService(RecebimentoRepository recebimentoRepo, LancamentoRepository lancamentoRepo, ContaBancariaRepository contaBancariaRepo) {
        this.recebimentoRepo = recebimentoRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
    }

    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAll() {
        return RecebimentoMapper.toDtoList(recebimentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAll(Pageable pageable) {
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

        Page<Recebimento> page = recebimentoRepo.findAll(effective);
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByLancamento(Integer lancamentoId, Pageable pageable) {
        if (lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lancamentoId é obrigatório");
        }

        if (!lancamentoRepo.existsById(lancamentoId)) {
            throw new ObjectNotFoundException("Lancamento id " + lancamentoId + "não encontrado");
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

        Page<Recebimento> page = recebimentoRepo.findByLancamento_Id(lancamentoId, effective);
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAllByLancamento(Integer lancamentoId) {
        return findAllByLancamento(lancamentoId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaBancariaId é obrigatório");
        }

        if (!lancamentoRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("Conta Bancaria id " + contaBancariaId + "não encontrado");
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

        Page<Recebimento> page = recebimentoRepo.findByContaBancaria_Id(contaBancariaId, effective);
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAllByContaBancaria(Integer contaBancariaId) {
        return findAllByContaBancaria(contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAllByLancamentoAndContaBancaria(Integer lancamentoId, Integer contaBancariaId, Pageable pageable) {
        if (!lancamentoRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("Lancamento id " + lancamentoId + " não encontrado");
        }
        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("Conta Bancaria id " + contaBancariaId + " não encontrado");
        }

        final Pageable effective;
        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        } else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<Recebimento> page = recebimentoRepo.findByLancamento_IdAndContaBancaria_Id(lancamentoId, contaBancariaId, effective);
        return RecebimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAllByLancamentoAndContaBancaria(Integer lancamentoId, Integer contaBancariaId) {
        return findAllByLancamentoAndContaBancaria(lancamentoId, contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public RecebimentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Recebimento é obrigatório");
        }

        return recebimentoRepo.findById(id)
                .map(RecebimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: id=" + id));
    }
}
