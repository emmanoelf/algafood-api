package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.RestauranteController;
import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public RestauranteDTOAssembler(){
        super(RestauranteController.class, RestauranteDTO.class);
    }

    @Override
    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);

        this.modelMapper.map(restaurante, restauranteDTO);

        restauranteDTO.add(this.linkToResource.linkToRestaurantes("restaurantes"));

        restauranteDTO.getCozinha().add(this.linkToResource.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteDTO.getEndereco().getCidade().add(this.linkToResource
                .linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteDTO.add(this.linkToResource.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));

        restauranteDTO.add(this.linkToResource.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

        if(restaurante.ativacaoPermitida()){
            restauranteDTO.add(this.linkToResource.linkToAtivarRestaurante(restaurante.getId(), "ativar"));
        }

        if(restaurante.inativacaoPermitida()){
            restauranteDTO.add(this.linkToResource.linkToInativarRestaurante(restaurante.getId(), "inativar"));
        }

        if(restaurante.aberturaPermitida()){
            restauranteDTO.add(this.linkToResource.linkToAberturaRestaurante(restaurante.getId(), "abrir"));
        }

        if(restaurante.fechamentoPermitido()){
            restauranteDTO.add(this.linkToResource.linkToFechamentoRestaurante(restaurante.getId(), "fechar"));
        }

        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(this.linkToResource.linkToRestaurantes());
    }
}
