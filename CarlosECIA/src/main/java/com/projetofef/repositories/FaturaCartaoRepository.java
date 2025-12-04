package com.projetofef.repositories;

import com.projetofef.domains.FaturaCartao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaCartaoRepository extends JpaRepository<FaturaCartao, Integer> {
    Page<FaturaCartao> findByCartaoCredito_Id(Integer cartaoCredito, Pageable pageable);
    boolean existsByCartaoCredito_Id(Integer cartaoCredito);
}
