package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class FaturaCartaoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "ValorTotal é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "ValorTotal deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "ValorTotal não pode ser negativo")
    private BigDecimal valorTotal;

    @NotNull(message = "CartaoCredito é obrigatório")
    private Integer cartaoCreditoId;

    @Min(value = 0, message = "StatusFatura inválido: use 0 (ABERTA), 1 (FECHADA), 2 (PAGA)")
    @Max(value = 2, message = "StatusFatura inválido: use 0 (ABERTA), 1 (FECHADA), 2 (PAGA)")
    private int StatusFatura;

    public FaturaCartaoDTO() {
    }

    public FaturaCartaoDTO(Integer id, BigDecimal valorTotal, Integer cartaoCreditoId, int statusFatura) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.cartaoCreditoId = cartaoCreditoId;
        StatusFatura = statusFatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getCartaoCreditoId() {
        return cartaoCreditoId;
    }

    public void setCartaoCreditoId(Integer cartaoCreditoId) {
        this.cartaoCreditoId = cartaoCreditoId;
    }

    public int getStatusFatura() {
        return StatusFatura;
    }

    public void setStatusFatura(int statusFatura) {
        StatusFatura = statusFatura;
    }
}