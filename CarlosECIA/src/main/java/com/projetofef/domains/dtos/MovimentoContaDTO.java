package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class MovimentoContaDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter, no máximo, 400 caracteres")
    private String tipo;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valdo deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @NotBlank(message = "Histórico é obrigatório")
    @Size(max = 400, message = "Histórico deve ter, no máximo, 400 caracteres")
    private String historico;

    @NotNull(message = "ContaBancária é obrigatório")
    private Integer contaBancariaId;

    @NotNull(message = "Investimento é obrigatório")
    private Integer investimentoId;

    @Min(value = 0, message = "TipoTransacao inválido: use 0 (CREDITO), 1 (DEBITO), 2 (TRANSACAO)")
    @Max(value = 2, message = "TipoTransacao inválido: use 0 (CREDITO), 1 (DEBITO), 2 (TRANSACAO)")
    private int TipoTransacao;

    public MovimentoContaDTO(Integer id, String tipo, BigDecimal valor, String historico, Integer contaBancariaId, Integer investimentoId, int tipoTransacao) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.historico = historico;
        this.contaBancariaId = contaBancariaId;
        this.investimentoId = investimentoId;
        TipoTransacao = tipoTransacao;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public String getHistorico() {
        return historico;
    }
    public void setHistorico(String historico) {
        this.historico = historico;
    }
    public Integer getContaBancariaId() {
        return contaBancariaId;
    }
    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }
    public Integer getInvestimentoId() {
        return investimentoId;
    }
    public void setInvestimentoId(Integer investimentoId) {
        this.investimentoId = investimentoId;
    }
    public int getTipoTransacao() {
        return TipoTransacao;
    }
    public void setTipoTransacao(int tipoTransacao) {
        TipoTransacao = tipoTransacao;
    }
}