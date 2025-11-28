package com.projetofef.carlosecia.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecebimentoDTO {
    public interface Create{}
    public interface Update{}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "O valor recebido é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valorRecebido;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;

    @NotNull(message = "É necessário informar uma conta bancária")
    private Integer contaBancariaId;

    @NotNull(message = "É necessário informar um lançamento")
    private Integer lancamentoId;

    public RecebimentoDTO(Integer id, Integer lancamentoId, LocalDate dataRecebimento, BigDecimal valorRecebimento, Integer contaBancariaId, String observacao) {}

    public RecebimentoDTO(Integer id,BigDecimal valorRecebido,
                          String observacao, Integer contaBancariaId, Integer lancamentoId) {
        this.id = id;
        this.valorRecebido = valorRecebido;
        this.observacao = observacao;
        this.contaBancariaId = contaBancariaId;
        this.lancamentoId = lancamentoId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorRecebido() {return valorRecebido;}
    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public String getObservacao() {return observacao;}
    public void setObservacao(String observacao) {this.observacao = observacao;}

    public Integer getContaBancariaId() {
        return contaBancariaId;
    }
    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }

    public Integer getLancamentoId() {
        return lancamentoId;
    }
    public void setLancamentoId(Integer lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

}
