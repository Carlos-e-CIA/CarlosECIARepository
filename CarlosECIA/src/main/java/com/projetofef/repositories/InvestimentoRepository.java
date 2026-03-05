package com.projetofef.repositories;

import com.projetofef.domains.Investimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {
    Page<Investimento> findByContaBancaria_Id(Integer contaBancariaId, Pageable pageable);
    Optional<Investimento> findByCodigo(String codigo);
    boolean existsByContaBancaria_Id(Integer contaBancariaId);
}