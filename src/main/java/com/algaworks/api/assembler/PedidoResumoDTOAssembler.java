package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.PedidoController;
import com.algaworks.api.model.PedidoResumoDTO;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public PedidoResumoDTOAssembler(){
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido){
        PedidoResumoDTO pedidoResumoDto = createModelWithId(pedido.getCodigo(), pedido);

        this.modelMapper.map(pedido, pedidoResumoDto);

        pedidoResumoDto.add(this.linkToResource.linkToPedidos("pedidos"));

        pedidoResumoDto.getRestaurante().add(this.linkToResource.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoResumoDto.getCliente().add(this.linkToResource.linkToUsuario(pedido.getCliente().getId()));

        return pedidoResumoDto;
    }
}
