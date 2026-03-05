package com.projetofef.services;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.Entidade;
import com.projetofef.domains.dtos.EntidadeDTO;
import com.projetofef.mappers.EntidadeMapper;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.EntidadeRepository;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.resources.exceptions.ResourceExceptionHandler;
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
public class EntidadeService {
    private static final int MAX_PAGE_SIZE = 200;

    private final EntidadeRepository entidadeRepo;
    private final UsuarioRepository usuarioRepo;
    private final LancamentoRepository lancamentoRepo;

    public EntidadeService(EntidadeRepository entidadeRepo, UsuarioRepository usuarioRepo, LancamentoRepository lancamentoRepo) {
        this.entidadeRepo = entidadeRepo;
        this.usuarioRepo = usuarioRepo;
        this.lancamentoRepo = lancamentoRepo;
    }

    @Transactional(readOnly = true)
    public List<EntidadeDTO> findAll() {
        return EntidadeMapper.toDtoList(entidadeRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<EntidadeDTO> findAll(Pageable pageable) {
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

        Page<Entidade> page = entidadeRepo.findAll(effective);
        return EntidadeMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<EntidadeDTO> findAllByUsuario(Integer usuarioId, Pageable pageable) {
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

        Page<Entidade> page = entidadeRepo.findByUsuario_Id(usuarioId, effective);
        return EntidadeMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<EntidadeDTO> findAllByUsuario(Integer usuarioId) {
        return findAllByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public EntidadeDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Entidade é obrigatório");
        }

        return entidadeRepo.findById(id)
                .map(EntidadeMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrado: id = " + id));
    }

    @Transactional(readOnly = true)
    public EntidadeDTO findByDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Documento do Entidade é obrigatório");
        }

        String normalizedDocumento = documento.trim();

        return entidadeRepo.findByDocumento(normalizedDocumento)
                .map(EntidadeMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: Documento = " + normalizedDocumento));
    }

    @Transactional
    public EntidadeDTO create(EntidadeDTO entidadeDTO) {

        if (entidadeDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do grupo são obrigatórios");
        }

        Integer usuarioId = entidadeDTO.getUsuarioId();
        
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));
        
        entidadeDTO.setId(null);
        Entidade entidade;
        try{
            entidade = EntidadeMapper.toEntity(entidadeDTO, usuario);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return EntidadeMapper.toDto(entidadeRepo.save(entidade));
    }

    @Transactional
    public EntidadeDTO update(Integer id, EntidadeDTO entidadeDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (entidadeDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do entidade são obrigatórios");
        }

        Entidade entidade = entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id = " + id));

        Integer usuarioId = entidadeDTO.getUsuarioId();

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id = " + usuarioId));
        
        EntidadeMapper.copyToEntity(entidadeDTO, entidade, usuario);

        return EntidadeMapper.toDto(entidadeRepo.save(entidade));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Entidade entidade = entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id = " + id));

        if (lancamentoRepo.existsByEntidade_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Entidade possui Lancamentos associados e não pode ser removida: id = " + id
            );
        }

        entidadeRepo.delete(entidade);
    }
}