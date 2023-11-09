package com.algaworks.api.assembler;

import com.algaworks.api.model.PedidoDTO;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toDTO(Pedido pedido){
        return this.modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionDTO(List<Pedido> pedidos){
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
