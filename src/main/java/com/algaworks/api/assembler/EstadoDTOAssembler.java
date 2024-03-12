package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.EstadoController;
import com.algaworks.api.model.EstadoDTO;
import com.algaworks.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public EstadoDTOAssembler(){
        super(EstadoController.class, EstadoDTO.class);
    }

    public EstadoDTO toModel(Estado estado){
        EstadoDTO estadoDto = createModelWithId(estado.getId(), estado);

        modelMapper.map(estado, estadoDto);

        estadoDto.add(this.linkToResource.linkToEstados("estados"));

        return estadoDto;
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities).add(this.linkToResource.linkToEstados());
    }
}
