package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.RestauranteController;
import com.algaworks.api.model.RestauranteResumoDTO;
import com.algaworks.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResumoDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public RestauranteResumoDTOAssembler(){
        super(RestauranteController.class, RestauranteResumoDTO.class);
    }

    @Override
    public RestauranteResumoDTO toModel(Restaurante restaurante) {
        RestauranteResumoDTO restauranteDto = createModelWithId(restaurante.getId(), restaurante);

        this.modelMapper.map(restaurante, restauranteDto);

        restauranteDto.add(this.linkToResource.linkToRestaurantes("restaurantes"));

        return restauranteDto;
    }
}
