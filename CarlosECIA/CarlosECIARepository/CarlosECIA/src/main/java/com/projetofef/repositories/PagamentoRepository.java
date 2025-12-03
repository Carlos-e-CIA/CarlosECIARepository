package com.projetofef.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.projetofef.domains.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    Page<Pagamento> findByContaBancaria_Id(Integer contaOrigem, Pageable pageable);
    Page<Pagamento> findByLancamento_Id(Integer idLancamento, Pageable pageable);
    Page<Pagamento> findByContaBancaria_IdAndLancamento_Id(Integer contaOrigem, Integer lancamentoId, Pageable pageable);
    boolean existsByContaBancaria_Id(Integer contaOrigem);
    boolean existsByLancamento_Id(Integer idLancamento);
}
