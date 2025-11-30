package com.projetofef.services;
import com.projetofef.repositories.EntidadeRepository;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.domains.Entidade;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.EntidadeDTO;
import com.projetofef.mappers.EntidadeMapper;
import com.projetofef.services.exceptions.ObjectNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@
@Service
static class EntidadeService {
    private static final int MAX_PAGE_SIZE = 200;

    private final EntidadeRepository entidadeRepo;
    private final UsuarioRepository usuarioRepo;

    public EntidadeService(EntidadeRepository entidadeRepo, UsuarioRepository usuarioRepo) {
        this.entidadeRepo = entidadeRepo;
        this.usuarioRepo = usuarioRepo;
    }
    @Transactional(readOnly = true)
    public List<EntidadeDTO> findAll() {
        return EntidadeMapper.toDtoList(entidadeRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<EntidadeDTO> findAll(Pageable pageable) {
        Page<Entidade> page = entidadeRepo.findAll(pageable);
        return EntidadeMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<EntidadeDTO> findByUsuario(Integer usuarioId, Pageable pageable) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }

        if (!usuarioRepo.existsById(usuarioId)) {
            throw new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId);
        }

        Page<Entidade> page = entidadeRepo.findByUsuario_Id(usuarioId, pageable);
        return EntidadeMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<EntidadeDTO> findByUsuario(Integer usuarioId) {
        return findByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }
    @Transactional(readOnly = true)
    public EntidadeDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Entidade é obrigatório");
        }
        Entidade entidade = entidadeRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Entidade não encontrada: id=" + id));

        return EntidadeMapper.toDto(entidade);
    }
    @Transactional
    public EntidadeDTO create(EntidadeDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da Entidade são obrigatórios");
        }

        if (dto.getUsuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado: id=" + dto.getUsuarioId()));

        dto.setId(null);
        Entidade entidade = EntidadeMapper.toEntity(dto, usuario);

        try {
            entidade = entidadeRepo.save(entidade);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível salvar a Entidade");
        }

        return EntidadeMapper.toDto(entidade);
    }
    @Transactional
    public EntidadeDTO update(Integer id, EntidadeDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da Entidade são obrigatórios");
        }

        Entidade entidade = entidadeRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Entidade não encontrada: id=" + id));

        if (dto.getUsuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado: id=" + dto.getUsuarioId()));

        EntidadeMapper.copyToEntity(dto, entidade, usuario);

        try {
            entidade = entidadeRepo.save(entidade);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível atualizar a Entidade");
        }

        return EntidadeMapper.toDto(entidade);
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Entidade entidade = entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id=" + id));

        try {
            entidadeRepo.delete(entidade);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível excluir a Entidade, existem dependências relacionadas.");
        }
    }
}






