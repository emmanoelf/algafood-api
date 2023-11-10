package com.algaworks.api.assembler;

import com.algaworks.api.model.input.PedidoInput;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput){
        return this.modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido){
        this.modelMapper.map(pedidoInput, pedido);
    }
}
