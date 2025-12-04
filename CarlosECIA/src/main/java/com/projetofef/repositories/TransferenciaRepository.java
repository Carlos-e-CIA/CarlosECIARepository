package com.projetofef.repositories;

import com.projetofef.domains.Transferencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {

    Page<Transferencia> findByContaBancaria_Id(Integer contaBancariaId, Pageable pageable);

    boolean existsByContaBancaria_Id(Integer contaBancariaId);
}