package com.projetofef.repositories;

import com.projetofef.domains.Lancamento;
import com.projetofef.domains.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {
    Page<Lancamento> findByUsuario_Id(Integer usuario, Pageable pageable);
    Page<Lancamento> findByEntidade_Id(Integer entidade, Pageable pageable);
    Page<Lancamento> findByCentroCusto_Id(Integer centroCusto, Pageable pageable);
    Page<Lancamento> findByContaBancaria_Id(Integer contaBancaria, Pageable pageable);
    Page<Lancamento> findByCartaoCredito_Id(Integer cartaoCredito, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndEntidade_IdAndCentroCusto_IdAndContaCusto_IdAndCartaoCredito_Id(Integer usuario, Integer entidade, Integer centroCusto, Integer contaBancaria, Integer cartaoCredito, Pageable pageable);
    boolean existsByfindByUsuario_Id(Integer usuario);
    boolean existsByEntidade_Id(Integer entidade);
    boolean existsByCentroCusto_Id(Integer centroCusto);
    boolean existsByContaBancaria_Id(Integer contaBancaria);
    boolean existsByCartaoCredito_Id(Integer cartaoCredito);
}
