package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.CidadeController;
import com.algaworks.api.model.CidadeDTO;
import com.algaworks.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public CidadeDTOAssembler(){
        super(CidadeController.class, CidadeDTO.class);
    }

    public CidadeDTO toModel(Cidade cidade){
        CidadeDTO cidadeDto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDto);

        cidadeDto.add(this.linkToResource.linkToCidades("cidades"));

        cidadeDto.getEstado().add(this.linkToResource.linkToEstado(cidade.getEstado().getId()));

        return cidadeDto;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }
}
