package com.projetofef.services;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Transferencia;
import com.projetofef.domains.dtos.TransferenciaDTO;
import com.projetofef.mappers.TransferenciaMapper;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.repositories.TransferenciaRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TransferenciaService {

    private static final int MAX_PAGE_SIZE = 200;

    private final TransferenciaRepository transferenciaRepo;
    private final ContaBancariaRepository contaBancariaRepo;

    public TransferenciaService(TransferenciaRepository transferenciaRepo,
                                ContaBancariaRepository contaBancariaRepo) {
        this.transferenciaRepo = transferenciaRepo;
        this.contaBancariaRepo = contaBancariaRepo;
    }

    @Transactional(readOnly = true)
    public List<TransferenciaDTO> findAll() {
        return TransferenciaMapper.toDtoList(transferenciaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaDTO> findAll(Pageable pageable) {
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

        Page<Transferencia> page = transferenciaRepo.findAll(effective);
        return TransferenciaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contaBancariaId é obrigatório");
        }

        if (!contaBancariaRepo.existsById(contaBancariaId)) {
            throw new ObjectNotFoundException("ContaBancaria id " + contaBancariaId + " não encontrada");
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

        Page<Transferencia> page = transferenciaRepo.findByContaBancaria_Id(contaBancariaId, effective);
        return TransferenciaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<TransferenciaDTO> findAllByContaBancaria(Integer contaBancariaId) {
        return findAllByContaBancaria(contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public TransferenciaDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Transferencia é obrigatório");
        }

        return transferenciaRepo.findById(id)
                .map(TransferenciaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferencia não encontrada: id=" + id));
    }

    @Transactional
    public TransferenciaDTO create(TransferenciaDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da transferência são obrigatórios");
        }

        Integer contaBancariaId = dto.getContaBancariaId();

        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta bancária é obrigatória");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrada: id=" + contaBancariaId));

        dto.setId(null);
        Transferencia transferencia;
        try {
            transferencia = TransferenciaMapper.toEntity(dto, contaBancaria);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return TransferenciaMapper.toDto(transferenciaRepo.save(transferencia));
    }

    @Transactional
    public TransferenciaDTO update(Integer id, TransferenciaDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da transferência são obrigatórios");
        }

        Transferencia transferencia = transferenciaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferencia não encontrada: id=" + id));

        Integer contaBancariaId = dto.getContaBancariaId();

        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta bancária é obrigatória");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrada: id=" + contaBancariaId));

        TransferenciaMapper.copyToEntity(dto, transferencia, contaBancaria);

        return TransferenciaMapper.toDto(transferenciaRepo.save(transferencia));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Transferencia transferencia = transferenciaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferencia não encontrada: id=" + id));

        try {
            transferenciaRepo.delete(transferencia);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Transferencia não pode ser removida devido a vínculos de integridade: id=" + id,
                    ex
            );
        }
    }
}