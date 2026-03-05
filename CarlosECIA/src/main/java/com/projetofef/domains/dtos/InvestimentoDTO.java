package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class InvestimentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 120, message = "Tipo deve ter, no máximo, 120 caracteres")
    private String tipo;

    @NotBlank(message = "Codigo é obrigatório")
    @Size(max = 20, message = "Codigo deve ter, no máximo, 20 caracteres")
    private String codigo;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @NotNull(message = "Juros é obrigatório")
    @Digits(integer = 6, fraction = 2, message = "Juros deve ter, no máximo, 6 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Juros não pode ser negativo")
    private BigDecimal juros;

    @NotNull(message = "ContaBancaria é obrigatório")
    private Integer contaBancariaId;

    public InvestimentoDTO() {
    }

    public InvestimentoDTO(Integer id, String tipo, String codigo, BigDecimal valor, BigDecimal juros, Integer contaBancariaId) {
        this.id = id;
        this.tipo = tipo;
        this.codigo = codigo;
        this.valor = valor;
        this.juros = juros;
        this.contaBancariaId = contaBancariaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public Integer getContaBancariaId() {
        return contaBancariaId;
    }

    public void setContaBancariaId(Integer contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }
}