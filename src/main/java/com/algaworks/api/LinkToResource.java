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

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel){
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        return new Link(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filterVariables)), rel);
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

    public Link linkToProdutos(Long restauranteId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToProdutos(Long id){
        return this.linkToProdutos(id, IanaLinkRelations.SELF.value());
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
        String restaurantesUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();

        return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes(){
        return this.linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                .listar(id)).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(Long id){
        return this.linkToRestauranteFormasPagamento(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).buscar(id)).withRel(rel);
    }

    public Link linkToCozinha(Long id){
        return linkToCozinha(id, IanaLinkRelations.SELF.value());
    }

    public Link linkToAberturaRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).abrir(id)).withRel(rel);
    }

    public Link linkToFechamentoRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).fechar(id)).withRel(rel);
    }

    public Link linkToAtivarRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).ativar(id)).withRel(rel);
    }

    public Link linkToInativarRestaurante(Long id, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).inativar(id)).withRel(rel);
    }

    public Link linkToFormasPagamento(String rel){
        return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToFormasPagamento(){
        return this.linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId))
                .withRel(rel);
    }

    public Link linkToFormaPagamentAssociacao(Long restauranteId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociarResponsavel(restauranteId, usuarioId))
                .withRel(rel);
    }

    public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociarResponsavel(restauranteId, null))
                .withRel(rel);
    }
}
