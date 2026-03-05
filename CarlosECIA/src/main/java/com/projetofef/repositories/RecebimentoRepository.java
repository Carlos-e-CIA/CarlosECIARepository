package com.projetofef.repositories;

import com.projetofef.domains.Recebimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {
    Page<Recebimento> findByLancamento_Id(Integer lancamentoId, Pageable pageable);
    Page<Recebimento> findByContaBancaria_Id(Integer contaBancariaId, Pageable pageable);
    Page<Recebimento> findByLancamento_IdAndContaBancaria_Id(Integer lancamentoId, Integer contaBancariaId, Pageable pageable);
    boolean existsByLancamento_Id(Integer lancamentoId);
    boolean existsByContaBancaria_Id(Integer contaBancariaId);
}
