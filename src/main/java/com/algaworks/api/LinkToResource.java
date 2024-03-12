package com.algaworks.api;

import com.algaworks.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class LinkToResource {
    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(){
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        return new Link(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filterVariables)), "pedidos");
    }


    public Link linkToRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(id)).withRel(rel);
    }

    public Link linkToRestaurante(Long id){
        return this.linkToRestaurante(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(id)).withRel(rel);
    }

    public Link linkToUsuario(Long id){
        return this.linkToUsuario(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel){
        return WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linktoUsuarios(){
        return this.linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(id)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long id){
        return this.linkToGruposUsuario(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(id)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long id){
        return this.linkToResponsaveisRestaurante(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscar(id, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long id){
        return this.linkToFormaPagamento(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(id)).withRel(rel);
    }

    public Link linkToCidade(Long id){
        return this.linkToCidade(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel){
        return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades(){
        return this.linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(id)).withRel(rel);
    }

    public Link linkToEstado(Long id){
        return this.linkToEstado(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel){
        return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados(){
        return this.linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId){
        return this.linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel){
        return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas(){
        return this.linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToConfirmacaoPedido(String codigoPedigo, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                .confirmar(codigoPedigo)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedigo, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                .cancelar(codigoPedigo)).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedigo, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                .entregar(codigoPedigo)).withRel(rel);
    }

    public Link linkToRestaurantes(String rel){
        return WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel(rel);
    }

    public Link linkToRestaurantes(){
        return this.linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                .listar(id)).withRel(rel);
    }

    public Link linkToCozinha(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).buscar(id)).withRel(rel);
    }

    public Link linkToCozinha(Long id){
        return linkToCozinha(id, IanaLinkRelations.SELF.value());
    }
}
