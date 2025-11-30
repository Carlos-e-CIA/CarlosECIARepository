package com.projetofef.services;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.UsuarioDTO;
import com.projetofef.mappers.UsuarioMapper;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private static final int MAX_PAGE_SIZE = 200;
    private final UsuarioRepository usuarioRepo;
    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return UsuarioMapper.toDtoList(usuarioRepo.findAll());
    }
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        Pageable effective = getEffectivePageable(pageable);
        Page<Usuario> page = usuarioRepo.findAll(effective);
        return UsuarioMapper.toDtoPage(page);
    }
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAllByNome(String nome, Pageable pageable) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório para filtro");
        }
        Pageable effective = getEffectivePageable(pageable);
        Page<Usuario> page = usuarioRepo.findByNomeContainingIgnoreCase(nome.trim(), effective);
        return UsuarioMapper.toDtoPage(page);
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllByNome(String nome) {
        return findAllByNome(nome, Pageable.unpaged()).getContent();
    }
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de Usuario é obrigatório");
        }
        return usuarioRepo.findById(id)
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id=" + id));
    }
    @Transactional(readOnly = true)
    public UsuarioDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email é obrigatório");
        }
        Optional<Usuario> usuario = usuarioRepo.findByEmail(email.trim());
        return usuario
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: email=" + email));
    }
    @Transactional
    public UsuarioDTO create(UsuarioDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email já cadastrado: " + dto.getEmail());
        }
        dto.setId(null);
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario = usuarioRepo.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }
    @Transactional
    public UsuarioDTO update(Integer id, UsuarioDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id=" + id));
        if (usuarioRepo.existsByEmail(dto.getEmail())
                && !usuario.getEmail().equals(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email já cadastrado: " + dto.getEmail());
        }
        UsuarioMapper.copyToEntity(dto, usuario);
        usuario = usuarioRepo.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id=" + id));
        usuarioRepo.delete(usuario);
    }
    private Pageable getEffectivePageable(Pageable pageable) {
        if (pageable == null || pageable.isUnpaged()) {
            return Pageable.unpaged();
        }
        return PageRequest.of(
                Math.max(0, pageable.getPageNumber()),
                Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                pageable.getSort()
        );
    }

}
