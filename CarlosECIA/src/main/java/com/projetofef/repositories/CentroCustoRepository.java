package com.projetofef.repositories;

import com.projetofef.domains.CentroCusto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Integer> {
    Page<CentroCusto> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Optional<CentroCusto> findByCodigo(String codigo);
    boolean existsByUsuario_Id(Integer usuarioId);
}