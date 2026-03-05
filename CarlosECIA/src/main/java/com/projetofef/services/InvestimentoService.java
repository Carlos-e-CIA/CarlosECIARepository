package com.projetofef.services;

import com.projetofef.domains.ContaBancaria;
import com.projetofef.domains.Investimento;
import com.projetofef.domains.dtos.InvestimentoDTO;
import com.projetofef.mappers.InvestimentoMapper;
import com.projetofef.repositories.MovimentoContaRepository;
import com.projetofef.repositories.InvestimentoRepository;
import com.projetofef.repositories.ContaBancariaRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
public class InvestimentoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final InvestimentoRepository investimentoRepo;
    private final ContaBancariaRepository contaBancariaRepo;
    private final MovimentoContaRepository movimentoContaRepo;

    public InvestimentoService(InvestimentoRepository investimentoRepo, ContaBancariaRepository contaBancariaRepo, MovimentoContaRepository movimentoContaRepo) {
        this.investimentoRepo = investimentoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
        this.movimentoContaRepo = movimentoContaRepo;
    }

    @Transactional(readOnly = true)
    public List<InvestimentoDTO> findAll() {
        return InvestimentoMapper.toDtoList(investimentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<InvestimentoDTO> findAll(Pageable pageable) {
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

        Page<Investimento> page = investimentoRepo.findAll(effective);
        return InvestimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<InvestimentoDTO> findAllByContaBancaria(Integer contaBancariaId, Pageable pageable) {
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

        Page<Investimento> page = investimentoRepo.findByContaBancaria_Id(contaBancariaId, effective);
        return InvestimentoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<InvestimentoDTO> findAllByContaBancaria(Integer contaBancariaId) {
        return findAllByContaBancaria(contaBancariaId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public InvestimentoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Investimento é obrigatório");
        }

        return investimentoRepo.findById(id)
                .map(InvestimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Investimento não encontrado: id=" + id));
    }

    @Transactional(readOnly = true)
    public InvestimentoDTO findByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo do Investimento é obrigatório");
        }

        String normalizedCodigo = codigo.trim();

        return investimentoRepo.findByCodigo(normalizedCodigo)
                .map(InvestimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Investimento não encontrado: Codigo=" + normalizedCodigo));
    }

    @Transactional
    public InvestimentoDTO create(InvestimentoDTO investimentoDTO) {

        if (investimentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do grupo são obrigatórios");
        }

        Integer contaBancariaId = investimentoDTO.getContaBancariaId();

        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ContaBancaria e a Editora são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrado: id=" + contaBancariaId));

        investimentoDTO.setId(null);
        Investimento investimento;
        try{
            investimento = InvestimentoMapper.toEntity(investimentoDTO, contaBancaria);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return InvestimentoMapper.toDto(investimentoRepo.save(investimento));
    }

    @Transactional
    public InvestimentoDTO update(Integer id, InvestimentoDTO investimentoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (investimentoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do investimento são obrigatórios");
        }

        Investimento investimento = investimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Investimento não encontrado: id=" + id));

        Integer contaBancariaId = investimentoDTO.getContaBancariaId();

        if (contaBancariaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ContaBancaria e a Editora são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(contaBancariaId)
                .orElseThrow(() -> new ObjectNotFoundException("ContaBancaria não encontrado: id=" + contaBancariaId));

        InvestimentoMapper.copyToEntity(investimentoDTO, investimento, contaBancaria);

        return InvestimentoMapper.toDto(investimentoRepo.save(investimento));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Investimento investimento = investimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Investimento não encontrado: id=" + id));

        if (movimentoContaRepo.existsByInvestimento_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Investimento possui MovimentoContas associados e não pode ser removido: id=" + id
            );
        }

        investimentoRepo.delete(investimento);
    }
}