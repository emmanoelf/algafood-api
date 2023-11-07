package com.algaworks.api.assembler;

import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toColletionDTO(Collection<FormaPagamento> formasPagamentos){
        return formasPagamentos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
