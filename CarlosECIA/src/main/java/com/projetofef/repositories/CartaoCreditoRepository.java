package com.projetofef.repositories;

import com.projetofef.domains.CartaoCredito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Integer> {
    Page<CartaoCredito> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Optional<CartaoCredito> findByApelido(String apelido);
    boolean existsByUsuario_Id(Integer usuarioId);
}