package com.projetofef.infra;

import com.projetofef.domains.enums.MeioPagamento;
import com.projetofef.domains.enums.TipoLancamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoLancamentoConverter implements AttributeConverter<TipoLancamento, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoLancamento tipoLancamento){
        return tipoLancamento == null ? null : tipoLancamento.getId();
    }

    @Override
    public TipoLancamento convertToEntityAttribute(Integer dbValue){
        return  TipoLancamento.toEnum(dbValue);
    }
}
