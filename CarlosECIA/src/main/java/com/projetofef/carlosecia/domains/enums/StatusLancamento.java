package com.projetofef.carlosecia.domains.enums;

public enum StatusLancamento {
    PENDENTE(0, "PENDENTE"), BAIXADO(1, "BAIXADO"), PARCIAL(2, "PARCIAL"), CANCELADO(3, "CANCELADO");
    private Integer id;
    private String descricao;

    StatusLancamento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static StatusLancamento toEnum(String descricao) {
        if(descricao == null) return null;
        for (StatusLancamento statusLancamento : StatusLancamento.values()) {
            if (descricao.equals(statusLancamento.getDescricao())) {
                return statusLancamento;
            }
        }
        throw new IllegalArgumentException("StatusLancamento Inválido!");
    }
}
