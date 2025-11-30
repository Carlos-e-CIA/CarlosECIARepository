package com.projetofef.carlosecia.mappers;

import com.projetofef.carlosecia.domains.Usuario;
import com.projetofef.carlosecia.domains.ContaBancaria;
import com.projetofef.carlosecia.domains.dtos.ContaBancariaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ContaBancariaMapper {
    private ContaBancariaMapper() {}

    public static ContaBancariaDTO toDto(ContaBancaria co) {
        if(co == null) return null;

        Integer idDto = co.getId();
        Integer usuarioId = (co.getUsuario() == null) ? null : co.getUsuario().getId();

        return new ContaBancariaDTO(
                idDto,
                co.getInstituicao(),
                co.getAgencia(),
                co.getNumero(),
                co.getApelido(),
                co.saldoInicial(),
                co.ativa(),
                usuarioId
        );
    }

    public static List<ContaBancariaDTO> toDtoList(Collection<ContaBancaria> entities) {
        if(entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ContaBancariaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ContaBancariaDTO> toDtoPage(Page<ContaBancaria> page) {
        List<ContaBancariaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static ContaBancaria toEntity(ContaBancariaDTO dto, Usuario usuario) {
        if(dto == null) return null;

        ContaBancaria co = new ContaBancaria();

        co.setId(dto.getId());
        co.setInstituicao(trim(dto.getInstituicao()));
        co.setAgencia(trim(dto.getAgencia()));
        co.setNumero(dto.getNumero());
        co.setApelido(trim(dto.getApelido()));
        co.setSaldoInicial(dto.getSaldoInicial());
        co.setAtiva(dto.getAtiva());
        co.setUsuario(usuario);

        return co;
    }

    public static ContaBancaria toEntity(ContaBancariaDTO dto, Function<Integer, Usuario> usuarioResolver) {
        if(dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(ContaBancariaDTO dto, ContaBancaria target, Usuario usuario) {
        if(dto == null || target == null) return;

        target.setInstituicao(trim(dto.getInstituicao()));
        target.setAgencia(trim(dto.getAgencia()));
        target.setNumero(dto.getNumero());
        target.setApelido(trim(dto.getApelido()));
        target.setSaldoInicial(dto.getSaldoInicial());
        target.setAtiva(dto.getAtiva());
        target.setUsuario(usuario);
    }

    public static void copyToEntity(ContaBancariaDTO dto, ContaBancaria target, Function <Integer, Usuario> usuarioResolver) {
        if(dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
