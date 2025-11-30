package com.projetofef.carlosecia.services;

import com.projetofef.carlosecia.domains.Usuario;
import com.projetofef.carlosecia.domains.ContaBancaria;
import com.projetofef.carlosecia.domains.dtos.ContaBancariaDTO;
import com.projetofef.carlosecia.mappers.ContaBancariaMapper;
import com.projetofef.carlosecia.repositories.MovimentoContaRepository;
import com.projetofef.carlosecia.repositories.LancamentoRepository;
import com.projetofef.carlosecia.repositories.PagamentoRepository;
import com.projetofef.carlosecia.repositories.RecebimentoRepository;
import com.projetofef.carlosecia.repositories.ContaBancariaRepository;
import com.projetofef.carlosecia.repositories.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.projetofef.carlosecia.services.exceptions.ObjectNotFoundException;
import java.util.List;

@Service
public class ContaBancariaService {
    private static final int MAX_PAGE_SIZE = 200;

    private final ContaBancariaRepository contaBancariaRepo;
    private final UsuarioRepository usuarioRepo;
    private final MovimentoContaRepository movimentoContaRepo;
    private final LancamentoRepository lancamentoRepo;
    private final PagamentoRepository pagamentoRepo;
    private final RecebimentoRepository recebimentoRepo;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepo, UsuarioRepository usuarioRepo, MovimentoContaRepository movimentoContaRepo, LancamentoRepository lancamentoRepo, PagamentoRepository pagementoRepo, RecebimentoRepository recebimentoRepo) {
        this.contaBancariaRepo = contaBancariaRepo;
        this.usuarioRepo = usuarioRepo;
        this.movimentoContaRepo = movimentoContaRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.pagamentoRepo = pagementoRepo;
        this.recebimentoRepo = recebimentoRepo;
    }

    @Transactional(readOnly = true)
    public List<ContaBancariaDTO> findAll() {
        return ContaBancariaMapper.toDtoList(contaBancariaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public Page<ContaBancariaDTO> findAll(Pageable pageable) {
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

        Page<ContaBancaria> page = contaBancariaRepo.findAll(effective);
        return ContaBancariaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public Page<ContaBancariaDTO> findAllByUsuario(Integer usuarioId, Pageable pageable) {
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

        Page<ContaBancaria> page = contaBancariaRepo.findByUsuario_Id(usuarioId, effective);
        return ContaBancariaMapper.toDtoPage(page);
    }

    @Transactional(readOnly = true)
    public List<ContaBancariaDTO> findAllByUsuario(Integer usuarioId) {
        return findAllByUsuario(usuarioId, Pageable.unpaged()).getContent();
    }

    @Transactional(readOnly = true)
    public ContaBancariaDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de ContaBancaria é obrigatório");
        }

        return contaBancariaRepo.findById(id)
                .map(ContaBancariaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ContaBancaria não encontrada: id=" + id));
    }

    @Transactional(readOnly = true)
    public ContaBancariaDTO findByNumero(Integer numero) {
        if (numero == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Numero do ContaBancaria é obrigatório");
        }

        return contaBancariaRepo.findByNumero(numero)
                .map(ContaBancariaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ContaBancaria não encontrado: Numero=" + numero));
    }

    @Transactional
    public ContaBancariaDTO create(ContaBancariaDTO contaBancariaDTO) {

        if (contaBancariaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuario são obrigatórios");
        }

        Integer usuarioId = contaBancariaDTO.getUsuarioId();

        if (usarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id=" + usuarioId));

        contaBancariaDTO.setId(null);
        ContaBancaria contaBancaria;
        try{
            contaBancaria = ContaBancariaMapper.toEntity(contaBancariaDTO, usuario);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return ContaBancariaMapper.toDto(contaBancariaRepo.save(contaBancaria));
    }

    @Transactional
    public ContaBancariaDTO update(Integer id, ContaBancariaDTO contaBancariaDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (contaBancariaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do contaBancaria são obrigatórios");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ContaBancaria não encontrado: id=" + id));

        Integer usuarioId = contaBancariaDTO.getUsuarioId();

        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuario é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado: id=" + usuarioId));

        ContaBancariaMapper.copyToEntity(contaBancariaDTO, contaBancaria, usuario);

        return ContaBancariaMapper.toDto(contaBancariaRepo.save(contaBancaria));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        ContaBancaria contaBancaria = contaBancariaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ContaBancaria não encontrado: id=" + id));

        if (movimentoContaRepo.existsByContaBancaria_Id(id)) {
            throw new DataIntegrityViolationException(
                    "ContaBancaria possui MovimentoContas associados e não pode ser removido: id=" + id
            );
        }

        if (lancamentoRepo.existsByContaBancaria_Id(id)) {
            throw new DataIntegrityViolationException(
                    "ContaBancaria possui Lancamentos associados e não pode ser removido: id=" + id
            );
        }

        if (pagamentoRepo.existsByContaBancaria_Id(id)) {
            throw new DataIntegrityViolationException(
                    "ContaBancaria possui Pagamentos associados e não pode ser removido: id=" + id
            );
        }

        if (recebimentoRepo.existsByContaBancaria_Id(id)) {
            throw new DataIntegrityViolationException(
                    "ContaBancaria possui Recebimentos associados e não pode ser removido: id=" + id
            );
        }

        contaBancariaRepo.delete(contaBancaria);
    }
}
