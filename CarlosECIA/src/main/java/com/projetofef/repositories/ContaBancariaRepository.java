package com.projetofef.carlosecia.repositories;

import com.projetofef.carlosecia.domains.ContaBancaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {
    Page<ContaBancaria> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Optional<ContaBancaria> findByNumero(Integer numero);
    boolean existsByUsuario_Id(Integer usuarioId);
}
