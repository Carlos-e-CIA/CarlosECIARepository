package com.projetofef.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.projetofef.carlosecia.domains.Pagamento;

import java.util.Optional;

public interface PagamentoRepository {
    Page<Pagamento> findByContaBancaria_Id(Integer contaOrigem, Pageable pageable);
    Page<Pagamento> findByLancamento_Id(Integer idLancamento, Pageable pageable);
    Page<Pagamento> findByContaBancaria_IdAndLancamento_Id(Integer contaOrigem, Integer idLancamento, Pageable pageable);
    boolean existsByContaBancaria_Id(Integer contaOrigem);
    boolean existsByLancamento_Id(Integer idLancamento);
}
