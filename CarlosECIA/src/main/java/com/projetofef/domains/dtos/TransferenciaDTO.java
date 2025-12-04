package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotNull(message = "Data da transferência é obrigatória")
    private LocalDate data;

    @NotNull(message = "Valor da transferência é obrigatório")
    @Digits(integer = 12, fraction = 2, message = "Valor deve ter no máximo 12 dígitos inteiros e 2 decimais")
    @PositiveOrZero(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;

    @NotNull(message = "Conta bancária é obrigatória")
    private Integer contaBancariaId;

    public TransferenciaDTO() {}

    public TransferenciaDTO(Integer id, LocalDate data, BigDecimal valor, String observacao, Integer contaBancariaId) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.observacao = observacao;
        this.contaBancariaId = contaBancariaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getContaBancariaId() {
        return contaBancariaId;
    }

    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }
}