package com.projetofef.infra;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.projetofef.domains.enums.MeioPagamento;

@Converter(autoApply = false)
public class MeioPagamentoConverter implements AttributeConverter<MeioPagamento, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MeioPagamento meioPagamento){
        return meioPagamento == null ? null : meioPagamento.getId();
    }

    @Override
    public MeioPagamento convertToEntityAttribute(Integer dbValue){
        return  MeioPagamento.toEnum(dbValue);
    }
}
