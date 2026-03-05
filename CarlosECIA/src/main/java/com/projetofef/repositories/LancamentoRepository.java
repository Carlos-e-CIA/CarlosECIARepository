package com.projetofef.repositories;

import com.projetofef.domains.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

    boolean existsByContaBancaria_Id(Integer contaBancariaId);
    boolean existsByUsuario_Id(Integer usuarioId);
    boolean existsByEntidade_Id(Integer entidadeId);
    boolean existsByCentroCusto_Id(Integer centroCustoId);
    boolean existsByCartaoCredito_Id(Integer cartaoCreditoId);
    Optional<Lancamento> findByDescricao(String descricao);

    Page<Lancamento> findByContaBancaria_Id(Integer contaBancariaId, Pageable pageable);
    Page<Lancamento> findByUsuario_Id(Integer usuarioId, Pageable pageable);
    Page<Lancamento> findByEntidade_Id(Integer entidadeId, Pageable pageable);
    Page<Lancamento> findByCentroCusto_Id(Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByCartaoCredito_Id(Integer cartaoCreditoId, Pageable pageable);

    Page<Lancamento> findByContaBancaria_IdAndUsuario_Id(Integer contaBancariaId, Integer usuarioId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndEntidade_Id(Integer contaBancariaId, Integer entidadeId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndCentroCusto_Id(Integer contaBancariaId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndEntidade_Id(Integer usuarioId, Integer entidadeId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndCentroCusto_Id(Integer usuarioId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndCartaoCredito_Id(Integer usuarioId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByEntidade_IdAndCentroCusto_Id(Integer entidadeId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByEntidade_IdAndCartaoCredito_Id(Integer entidadeId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByCentroCusto_IdAndCartaoCredito_Id(Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);

    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndEntidade_Id(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndCentroCusto_Id(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer usuarioId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndEntidade_IdAndCentroCusto_Id(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndEntidade_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndEntidade_IdAndCentroCusto_Id(Integer usuarioId, Integer entidadeId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndEntidade_IdAndCartaoCredito_Id(Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);

    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCentroCusto_Id(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer usuarioId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByContaBancaria_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);
    Page<Lancamento> findByUsuario_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);

    Page<Lancamento> findByContaBancaria_IdAndUsuario_IdAndEntidade_IdAndCentroCusto_IdAndCartaoCredito_Id(Integer contaBancariaId, Integer usuarioId, Integer entidadeId, Integer centroCustoId, Integer cartaoCreditoId, Pageable pageable);
}