package com.algaworks.api.assembler;

import com.algaworks.api.controller.CozinhaController;
import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTOAssembler(){
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha){
        CozinhaDTO cozinhaDto = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaDto);

        cozinhaDto.add(WebMvcLinkBuilder.linkTo(CozinhaController.class).withSelfRel());

        return cozinhaDto;
    }

    @Override
    public CollectionModel<CozinhaDTO> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities);
    }
}
