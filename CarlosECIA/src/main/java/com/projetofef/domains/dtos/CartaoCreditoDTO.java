package com.projetofef.domains.dtos;

import jakarta.validation.constraints.*;

public class CartaoCreditoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Integer id;

    @NotBlank(message = "Bandeira é obrigatória")
    @Size(max = 50, message = "Bandeira deve ter, no máximo, 50 caracteres")
    private String bandeira;

    @NotBlank(message = "Emissor é obrigatório")
    @Size(max = 120, message = "Emissor deve ter, no máximo, 120 caracteres")
    private String emissor;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(max = 120, message = "Apelido deve ter, no máximo, 120 caracteres")
    private String apelido;

    @NotNull(message = "Ativo é obrigatório")
    private Character ativo;

    @NotNull(message = "Usuario é obrigatório")
    private Integer usuarioId;

    public CartaoCreditoDTO() {
    }

    public CartaoCreditoDTO(Integer id, String bandeira, String emissor, String apelido, Character ativo, Integer usuarioId) {
        this.id = id;
        this.bandeira = bandeira;
        this.emissor = emissor;
        this.apelido = apelido;
        this.ativo = ativo;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}