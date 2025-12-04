package com.projetofef.mappers;

import com.projetofef.domains.Usuario;
import com.projetofef.domains.dtos.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UsuarioMapper {
    private UsuarioMapper() {}

    public static UsuarioDTO toDto(Usuario u) {
        if (u == null) return null;

        return new UsuarioDTO(
                u.getId(),
                trim(u.getNome()),
                u.getEmail(),
                u.getCriadoEm()
        );
    }
    public static List<UsuarioDTO> toDtoList(Collection<Usuario> list) {
        if (list == null) return List.of();
        return list.stream()
                .filter(Objects::nonNull)
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }
    public static Page<UsuarioDTO> toDtoPage(Page<Usuario> page) {
        List<UsuarioDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario u = new Usuario();
        u.setId(dto.getId());
        u.setNome(trim(dto.getNome()));
        u.setEmail(dto.getEmail());
        u.setCriadoEm(dto.getCriadoEm());

        return u;
    }
    public static void copyToEntity(UsuarioDTO dto, Usuario target) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setEmail(dto.getEmail());
        target.setCriadoEm(dto.getCriadoEm());
    }
    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

}
