package com.projetofef.repositories;

import com.projetofef.domains.MovimentoConta;
import com.projetofef.domains.ContaBancaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentoContaRepository extends JpaRepository<MovimentoConta, Integer> {
    Page<MovimentoConta> findByContaBancaria_Id(Integer contaBancariaId, Pageable pageable);
    Page<MovimentoConta> findByInvestimento_Id(Integer investimentoId, Pageable pageable);
    Page<MovimentoConta> findByContaBancaria_IdAndInvestimento_Id(Integer contaBancariaId, Integer investimentoId, Pageable pageable);
    Optional<MovimentoConta> findByHistorico(String historico);
    boolean existsByContaBancaria_Id(Integer contaBancariaId);
    boolean existsByInvestimento_Id(Integer investimentoId);
}