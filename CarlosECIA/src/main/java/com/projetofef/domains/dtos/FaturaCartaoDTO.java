package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class FaturaCartaoDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = FaturaCartaoDTO.Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = FaturaCartaoDTO.Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "A competencia é obrigatória")
    @Size(max = 100)
    private String competencia;

    @NotNull(message = "Valor total é obrigatório")
    @Digits(integer = 12, fraction = 2)
    @Positive(message = "O valor deve ser maior que zero")
    @PositiveOrZero(message = "Valor total não pode ser negativo")
    private BigDecimal valorTotal;

    @NotNull(message = "O cartão de crédito é obrigatório")
    private  Integer cartaoCredito;

    @Min(value = 0)
    @Max(value = 2)
    private int statusFatura;

    public FaturaCartaoDTO(Integer id, String competencia, BigDecimal valorTotal, Integer cartaoCredito, int statusFatura) {
        this.id = id;
        this.competencia = competencia;
        this.valorTotal = valorTotal;
        this.cartaoCredito = cartaoCredito;
        this.statusFatura = statusFatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(Integer cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public int getStatusFatura() {
        return statusFatura;
    }

    public void setStatusFatura(int statusFatura) {
        this.statusFatura = statusFatura;
    }
}
