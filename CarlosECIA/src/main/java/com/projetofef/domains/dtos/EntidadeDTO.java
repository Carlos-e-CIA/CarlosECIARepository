package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

public class EntidadeDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter, no máximo, 120 caracteres")
    private String nome;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 14, message = "Documento deve ter, no máximo, 14 caracteres")
    private String documento;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 120, message = "Tipo deve ter, no máximo, 120 caracteres")
    private String tipo;

    @NotNull(message = "Usuario é obragtório")
    private Integer usuarioId;

    public EntidadeDTO() {
    }

    public EntidadeDTO(Integer id, String nome, String documento, String tipo, Integer usuarioId) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}