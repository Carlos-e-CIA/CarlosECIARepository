package com.projetofef.services;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.UsuarioDTO;
import com.projetofef.mappers.UsuarioMapper;
import com.projetofef.repositories.UsuarioRepository;
import com.projetofef.repositories.LancamentoRepository;
import com.projetofef.repositories.ContaBancariaRepository;
import com.projetofef.repositories.EntidadeRepository;
import com.projetofef.repositories.CentroCustoRepository;
import com.projetofef.repositories.CartaoCreditoRepository;
import com.projetofef.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepo;
    private final LancamentoRepository lancamentoRepo;
    private final ContaBancariaRepository contaBancariaRepo;
    private final EntidadeRepository entidadeRepo;
    private final CentroCustoRepository centroCustoRepo;
    private final CartaoCreditoRepository cartaoCreditoRepo;

    public UsuarioService(UsuarioRepository usuarioRepo, LancamentoRepository lancamentoRepo, ContaBancariaRepository contaBancariaRepo, EntidadeRepository entidadeRepo, CentroCustoRepository centroCustoRepo, CartaoCreditoRepository cartaoCreditoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.contaBancariaRepo = contaBancariaRepo;
        this.entidadeRepo = entidadeRepo;
        this.centroCustoRepo = centroCustoRepo;
        this.cartaoCreditoRepo = cartaoCreditoRepo;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll(){
        return UsuarioMapper.toDtoList(usuarioRepo.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id é obrigatório");
        }

        return usuarioRepo.findById(id)
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id = " + id));
    }

    @Transactional
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuario são obrigatórios");
        }

        usuarioDTO.setId(null);
        Usuario usuario;
        try{
            usuario = UsuarioMapper.toEntity(usuarioDTO);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }

    @Transactional
    public UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        if (usuarioDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuario são obrigatórios");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id = " + id));

        UsuarioMapper.copyToEntity(usuarioDTO, usuario);

        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }

    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuario não encontrado: id = " + id));

        if (lancamentoRepo.existsByUsuario_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Usuario possui lancamentos associados e não pode ser removido: id = " + id
            );
        }

        if (contaBancariaRepo.existsByUsuario_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Usuario possui contaBancarias associadas e não pode ser removido: id = " + id
            );
        }

        if (entidadeRepo.existsByUsuario_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Usuario possui entidades associadas e não pode ser removido: id = " + id
            );
        }

        if (centroCustoRepo.existsByUsuario_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Usuario possui centroCustos associados e não pode ser removido: id = " + id
            );
        }

        if (cartaoCreditoRepo.existsByUsuario_Id(id)) {
            throw new DataIntegrityViolationException(
                    "Usuario possui cartaoCreditos associados e não pode ser removido: id = " + id
            );
        }

        usuarioRepo.delete(usuario);
    }
}