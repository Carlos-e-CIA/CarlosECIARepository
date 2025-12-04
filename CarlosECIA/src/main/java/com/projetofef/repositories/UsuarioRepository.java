package com.projetofef.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.projetofef.domains.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByEmail(String email);
}
