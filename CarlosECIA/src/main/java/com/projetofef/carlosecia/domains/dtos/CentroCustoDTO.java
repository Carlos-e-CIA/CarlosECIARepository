package com.projetofef.carlosecia.domains.dtos;

import jakarta.validation.constraints.*;

public class CentroCustoDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter, no máximo, 120 caracteres")
    private String nome;

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 20, message = "Código deve ter, no máximo, 20 caracteres")
    private String codigo;

    @NotNull(message = "Campo 'ativo' é obrigatório")
    @Min(value = 0, message = "Valor inválido: use 0 (inativo) ou 1 (ativo)")
    @Max(value = 1, message = "Valor inválido: use 0 (inativo) ou 1 (ativo)")
    private Integer ativo;

    @NotNull(message = "Usuário é obrigatório")
    private Integer usuarioId;

    public CentroCustoDTO() {}

    public CentroCustoDTO(Integer id, String nome, String codigo, Integer ativo, Integer usuarioId) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.ativo = ativo;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}