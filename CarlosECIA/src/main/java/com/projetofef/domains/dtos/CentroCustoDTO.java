package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

public class CentroCustoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = EntidadeDTO.Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = EntidadeDTO.Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter, no máximo, 120 caracteres")
    private String nome;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 20, message = "Documento deve ter, no máximo, 20 caracteres")
    private String codigo;

    @NotNull(message = "Ativo é obrigatório")
    private Integer ativo;

    @NotNull(message = "Usuario é obragtório")
    private Integer usuarioId;

    public CentroCustoDTO() {
    }

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