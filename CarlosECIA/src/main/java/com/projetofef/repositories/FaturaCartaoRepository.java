package com.projetofef.repositories;

import com.projetofef.domains.FaturaCartao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FaturaCartaoRepository extends JpaRepository<FaturaCartao, Integer> {
    Page<FaturaCartao> findByCartaoCredito_Id(Integer cartaoCreditoId, Pageable pageable);
    Optional<FaturaCartao> findByValorTotal(BigDecimal valorTotal);
    boolean existsByCartaoCredito_Id(Integer cartaoCreditoId);
}