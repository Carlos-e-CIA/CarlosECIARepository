package com.projetofef.repositories;

import com.projetofef.domains.Recebimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {
    Page<Recebimento> findByLancamentoId(Integer lancamentoId, Pageable pageable);
    Page<Recebimento> findByContaBancariaId(Integer contaBancariaId, Pageable pageable);
    Page<Recebimento> findByLancamentoIdAndContaBancariaId(Integer lancamentoId, Integer contaDestinoId, Pageable pageable);
    Optional<Recebimento> findByObservacao(String observacao);
    boolean existsByLancamento_Id(Integer lancamentoId);
    boolean existsByContaBancaria_Id(Integer contaBancariaId);
}