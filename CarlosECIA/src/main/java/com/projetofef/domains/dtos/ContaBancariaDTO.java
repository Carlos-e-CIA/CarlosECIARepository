package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ContaBancariaDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Instituição é obrigatória")
    @Size(max = 120, message = "Instituição deve ter, no máximo, 120 caracteres")
    private String instituicao;

    @NotBlank(message = "Agência é obrigatória")
    @Size(max = 5, message = "Agência deve ter, no máximo, 5 caracteres")
    private String agencia;

    @NotNull(message = "Número é obrigatório")
    private Integer numero;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(max = 120, message = "Apelido deve ter, no máximo, 120 caracteres")
    private String apelido;

    @NotNull(message = "Saldo Inicial é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Saldo Inicial deve ter, no máximo, 15 casas inteiras e 2 decimais")
    @PositiveOrZero(message = "Saldo Inicial não pode ser negativo")
    private BigDecimal saldoInicial;

    @NotNull(message = "Ativa é obrigatória")
    private Character ativa;

    @NotNull(message = "Usuário é obrigatório")
    private Integer usuarioId;

    ContaBancariaDTO() {}

    public ContaBancariaDTO(Integer id, String instituicao, String agencia, Integer numero, String apelido, BigDecimal saldoInicial, Character ativa, Integer usuarioId) {
        this.id = id;
        this.instituicao = instituicao;
        this.agencia = agencia;
        this.numero = numero;
        this.apelido = apelido;
        this.saldoInicial = saldoInicial;
        this.ativa = ativa;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Character getAtiva() {
        return ativa;
    }

    public void setAtiva(Character ativa) {
        this.ativa = ativa;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}