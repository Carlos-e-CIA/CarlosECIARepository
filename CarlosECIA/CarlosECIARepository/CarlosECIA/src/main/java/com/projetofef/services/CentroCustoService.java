package com.projetofef.services;

import com.projetofef.domains.CentroCusto;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.CentroCustoDTO;
import com.projetofef.mappers.CentroCustoMapper;
import com.projetofef.repositories.CentroCustoRepository;
import com.projetofef.repositories.UsuarioRepository;
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
public class CentroCustoService {

    private static final int MAX_PAGE_SIZE = 200;

    private final CentroCustoRepository centroCustoRepo;
    private final UsuarioRepository usuarioRepo;

    public CentroCustoService(CentroCustoRepository centroCustoRepo, UsuarioRepository usuarioRepo) {
        this.centroCustoRepo = centroCustoRepo;
        this.usuarioRepo = usuarioRepo;
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
        } else {
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
            throw new ObjectNotFoundException("Usuário id " + usuarioId + " não encontrado");
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
                        new ObjectNotFoundException("Centro de Custo não encontrado: id=" + id));
    }

    @Transactional
    public CentroCustoDTO create(CentroCustoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do Centro de Custo são obrigatórios");
        }

        Integer usuarioId = dto.getUsuarioId();

        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId));

        dto.setId(null);
        CentroCusto entity = CentroCustoMapper.toEntity(dto, usuario);

        return CentroCustoMapper.toDto(centroCustoRepo.save(entity));
    }

    @Transactional
    public CentroCustoDTO update(Integer id, CentroCustoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do Centro de Custo são obrigatórios");
        }

        CentroCusto entity = centroCustoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de Custo não encontrado: id=" + id));

        Integer usuarioId = dto.getUsuarioId();
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId));

        CentroCustoMapper.copyToEntity(dto, entity, usuario);

        return CentroCustoMapper.toDto(centroCustoRepo.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        CentroCusto entity = centroCustoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de Custo não encontrado: id=" + id));

        centroCustoRepo.delete(entity);
    }
}