package com.projetofef.carlosecia.repositories;

import com.projetofef.carlosecia.domains.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Integer> {
}
