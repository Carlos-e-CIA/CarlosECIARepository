package com.projetofef.repositories;

import com.projetofef.domains.Entidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntidadeRepository {
    Page<Entidade> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Page<Entidade> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByUsuario_Id(Integer usuarioId);
    boolean existsByDocumento(String documento);
}
