package com.algaworks.api.assembler;

import com.algaworks.api.controller.CidadeController;
import com.algaworks.api.controller.EstadoController;
import com.algaworks.api.model.CidadeDTO;
import com.algaworks.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{
    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTOAssembler(){
        super(CidadeController.class, CidadeDTO.class);
    }

    public CidadeDTO toModel(Cidade cidade){
        CidadeDTO cidadeDto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDto);

        cidadeDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeDto.getId())).withSelfRel());

        cidadeDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeDto.getEstado().add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EstadoController.class).buscar(cidadeDto.getEstado().getId())).withSelfRel());

        return cidadeDto;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }
}
