package com.projetofef.services;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.CentroCusto;
import com.projetofef.domains.dtos.CentroCustoDTO;
import com.projetofef.mappers.CentroCustoMapper;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.CentroCustoRepository;
import com.projetofef.repositories.UsuarioRepository;
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
public class CentroCustoService {
    private static final int MAX_PAGE_SIZE = 200;

    private final CentroCustoRepository centroCustoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LancamentoRepository lancamentoRepo;

    public CentroCustoService(CentroCustoRepository centroCustoRepo, UsuarioRepository usuarioRepo, LancamentoRepository lancamentoRepo) {
        this.centroCustoRepo = centroCustoRepo;
        this.usuarioRepo = usuarioRepo;
        this.lancamentoRepo = lancamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<CentroCustoDTO> findAll() {
        return CentroCustoMapper.toDtoList(centroCustoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<CentroCustoDTO> findAll(Pageable pageable) {
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

        Page<CentroCusto> page = centroCustoRepo.findAll(effective);
        return CentroCustoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<CentroCustoDTO> findAllByUsuario(Integer usuarioId, Pageable pageable) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }

        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuario id " + usuarioId + "não encontrado");
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

        Page<CentroCusto> page = centroCustoRepo.findByUsuario_Id(usuarioId, effective);
        return CentroCustoMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<CentroCustoDTO> findAllByUsuario(Integer usuarioId) {
        return findAllByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public CentroCustoDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de CentroCusto é obrigatório");
        }

        return centroCustoRepo.findById(id)
                .map(CentroCustoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CentroCusto não encontrado: id = " + id));
    }

    @Transactional(readOnly = true)
    public CentroCustoDTO findByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo do CentroCusto é obrigatório");
        }

        String normalizedCodigo = codigo.trim();

        return centroCustoRepo.findByCodigo(normalizedCodigo)
                .map(CentroCustoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CentroCusto não encontrada: Codigo = " + normalizedCodigo));
    }

    @Transactional
    public CentroCustoDTO create(CentroCustoDTO centroCustoDTO) {

        if (centroCustoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do grupo são obrigatórios");
        }

        Integer usuarioId = centroCustoDTO.getUsuarioId();

        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));

        centroCustoDTO.setId(null);
        CentroCusto centroCusto;
        try{
            centroCusto = CentroCustoMapper.toEntity(centroCustoDTO, usuario);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return CentroCustoMapper.toDto(centroCustoRepo.save(centroCusto));
    }

    @Transactional
    public CentroCustoDTO update(Integer id, CentroCustoDTO centroCustoDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (centroCustoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do centroCusto são obrigatórios");
        }

        CentroCusto centroCusto = centroCustoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CentroCusto não encontrada: id = " + id));

        Integer usuarioId = centroCustoDTO.getUsuarioId();

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));

        CentroCustoMapper.copyToEntity(centroCustoDTO, centroCusto, usuario);

        return CentroCustoMapper.toDto(centroCustoRepo.save(centroCusto));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        CentroCusto centroCusto = centroCustoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("CentroCusto não encontrada: id = " + id));

        if (lancamentoRepo.existsByCentroCusto_Id(id)) {
            throw new DataIntegrityViolationException(
                    "CentroCusto possui Lancamentos associados e não pode ser removida: id = " + id
            );
        }

        centroCustoRepo.delete(centroCusto);
    }
}