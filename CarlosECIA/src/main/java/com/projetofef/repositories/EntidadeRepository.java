package com.projetofef.repositories;

import com.projetofef.domains.Entidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Integer> {
    Page<Entidade> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Optional<Entidade> findByDocumento(String documento);
    boolean existsByUsuario_Id(Integer usuarioId);
}
