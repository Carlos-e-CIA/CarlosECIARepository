package com.projetofef.mappers;

import com.projetofef.domains.Entidade;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.EntidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EntidadeMapper {
    private EntidadeMapper() {}

    public static EntidadeDTO toDto(Entidade e) {
        if (e == null) return null;

        Integer idDto = e.getId();
        Integer idUsuario = (e.getUsuario() == null) ? null : e.getUsuario().getId();

        return new EntidadeDTO(
                idDto,
                idUsuario,
                e.getNome(),
                e.getDocumento(),
                e.getTipo()
        );
    }
    public static List<EntidadeDTO> toDtoList(Collection<Entidade> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(EntidadeMapper::toDto)
                .collect(Collectors.toList());
    }
    public static Page<EntidadeDTO> toDtoPage(Page<Entidade> page) {
        List<EntidadeDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
    public static Entidade toEntity(EntidadeDTO dto, Usuario usuario) {
        if (dto == null) return null;

        Entidade e = new Entidade();
        e.setId(dto.getId());
        e.setUsuario(usuario);
        e.setNome(trim(dto.getNome()));
        e.setDocumento(trim(dto.getDocumento()));
        e.setTipo(trim(dto.getTipo()));

        return e;
    }

    public static Entidade toEntity(EntidadeDTO dto, Function<Integer, Usuario> usuarioResolver) {
        if (dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(EntidadeDTO dto, Entidade target, Usuario usuario) {
        if (dto == null || target == null) return;

        target.setUsuario(usuario);
        target.setNome(trim(dto.getNome()));
        target.setDocumento(trim(dto.getDocumento()));
        target.setTipo(trim(dto.getTipo()));
    }

    public static void copyToEntity(EntidadeDTO dto, Entidade target, Function<Integer, Usuario> usuarioResolver) {
        if (dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }
    private static String trim(String s) {return (s == null) ? null : s.trim();
    }

}
