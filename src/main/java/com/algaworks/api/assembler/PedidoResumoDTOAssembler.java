package com.algaworks.api.assembler;

import com.algaworks.api.controller.PedidoController;
import com.algaworks.api.controller.RestauranteController;
import com.algaworks.api.controller.UsuarioController;
import com.algaworks.api.model.PedidoResumoDTO;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTOAssembler(){
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido){
        PedidoResumoDTO pedidoResumoDto = createModelWithId(pedido.getId(), pedido);

        this.modelMapper.map(pedido, pedidoResumoDto);

        pedidoResumoDto.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

        pedidoResumoDto.getRestaurante().add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoResumoDto.getCliente().add(WebMvcLinkBuilder.linkTo
                (WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoResumoDto;
    }
}
