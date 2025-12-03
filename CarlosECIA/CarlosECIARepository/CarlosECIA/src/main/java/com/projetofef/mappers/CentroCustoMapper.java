package com.projetofef.mappers;

import com.projetofef.domains.CentroCusto;
import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.CentroCustoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CentroCustoMapper {

    private CentroCustoMapper() {}

    public static CentroCustoDTO toDto(CentroCusto ce) {
        if (ce == null) return null;

        Integer idDto = e.getId();
        Integer usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getId();

        return new CentroCustoDTO(
                idDto,
                ce.getNome(),
                ce.getCodigo(),
                ce.getAtivo(),
                usuarioId
        );
    }

    public static List<CentroCustoDTO> toDtoList(Collection<CentroCusto> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(CentroCustoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<CentroCustoDTO> toDtoPage(Page<CentroCusto> page) {
        List<CentroCustoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static CentroCusto toEntity(CentroCustoDTO dto, Usuario usuario) {
        if (dto == null) return null;

        CentroCusto ce = new CentroCusto();

        ce.setId(dto.getId());
        ce.setNome(trim(dto.getNome()));
        ce.setCodigo(trim(dto.getCodigo()));
        ce.setAtivo(dto.getAtivo());
        ce.setUsuario(usuario);

        return ce;
    }

    public static CentroCusto toEntity(CentroCustoDTO dto, Function<Integer, Usuario> usuarioResolver) {
        if (dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(CentroCustoDTO dto, CentroCusto target, Usuario usuario) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setCodigo(trim(dto.getCodigo()));
        target.setAtivo(dto.getAtivo());
        target.setUsuario(usuario);
    }

    public static void copyToEntity(CentroCustoDTO dto, CentroCusto target, Function<Integer, Usuario> usuarioResolver) {
        if (dto == null || target == null) return;

        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
