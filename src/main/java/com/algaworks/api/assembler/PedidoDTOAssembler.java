package com.algaworks.api.assembler;

import com.algaworks.api.controller.*;
import com.algaworks.api.model.PedidoDTO;
import com.algaworks.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTOAssembler(){
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido){
        PedidoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);

        this.modelMapper.map(pedido, pedidoDto);

        TemplateVariables pageVariables = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        pedidoDto.add(new Link(UriTemplate.of(pedidosUrl, pageVariables.concat(filterVariables)), "pedidos"));

        pedidoDto.getRestaurante().add(WebMvcLinkBuilder.linkTo
                (WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoDto.getCliente().add(WebMvcLinkBuilder.linkTo
                (WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscar(pedido.getCliente().getId())).withSelfRel());

        pedidoDto.getEndereco().getCidade().add(WebMvcLinkBuilder.linkTo
                (WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoDto.getFormaPagamento().add(WebMvcLinkBuilder.linkTo
                (WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
                        .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        pedidoDto.getItens().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo
                    (WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                            .buscar(pedido.getRestaurante().getId(), item.getProdutoId())).withRel("produto"));
        });

        return pedidoDto;
    }
}
