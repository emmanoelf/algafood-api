package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.*;
import com.algaworks.api.model.PedidoDTO;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public PedidoDTOAssembler(){
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido){
        PedidoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);

        this.modelMapper.map(pedido, pedidoDto);

        pedidoDto.add(this.linkToResource.linkToPedidos("pedidos"));

        if(pedido.podeSerConfirmado()){
            pedidoDto.add(this.linkToResource.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmacao"));
        }

        if(pedido.podeSerCancelado()){
            pedidoDto.add(this.linkToResource.linkToCancelamentoPedido(pedido.getCodigo(), "cancelamento"));
        }

        if(pedido.podeSerEntregue()){
            pedidoDto.add(this.linkToResource.linkToEntregaPedido(pedido.getCodigo(), "entrega"));
        }

        pedidoDto.getRestaurante().add(this.linkToResource.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDto.getCliente().add(this.linkToResource.linkToUsuario(pedido.getCliente().getId()));

        pedidoDto.getEndereco().getCidade().add(this.linkToResource.linkToCidade(pedido
                .getEnderecoEntrega().getCidade().getId()));

        pedidoDto.getFormaPagamento().add(this.linkToResource.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoDto.getItens().forEach(item -> {
            item.add(this.linkToResource.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoDto;
    }
}
