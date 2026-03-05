package com.projetofef.infra;

import com.projetofef.domains.enums.TipoTransacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoTransacaoConverter implements AttributeConverter<TipoTransacao, Integer>{
    @Override
    public Integer convertToDatabaseColumn(TipoTransacao tipoTransacao){
        return tipoTransacao == null ? null : tipoTransacao.getId();
    }

    @Override
    public TipoTransacao convertToEntityAttribute(Integer dbValue){
        return TipoTransacao.toEnum(dbValue);
    }
}