package com.projetofef.carlosecia.domains.enums;

public enum tipoTransacao {
    CREDITO(0,"CREDITO"), DEBITO(1, "DEBITO"), TRANSACAO(2, "TRANSACAO");

    private Integer id;
    private String descricao;

    tipoTransacao(Integer id, String descricao) {
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

    public static tipoTransacao toEnum(String descricao) {
        if(descricao == null) return null;
        for(tipoTransacao tipoTransacao : tipoTransacao.values()) {
            if(descricao.equals(tipoTransacao.getDescricao())){
                return tipoTransacao;
            }
        }
        throw new IllegalArgumentException("Transação Inválida!");
    }
}


