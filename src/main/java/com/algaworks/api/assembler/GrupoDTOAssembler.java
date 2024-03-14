package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.GrupoController;
import com.algaworks.api.model.GrupoDTO;
import com.algaworks.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public GrupoDTOAssembler(){
        super(GrupoController.class, GrupoDTO.class);
    }

    @Override
    public GrupoDTO toModel(Grupo grupo){
        GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);

        this.modelMapper.map(grupo, grupoDTO);

        grupoDTO.add(this.linkToResource.linkToGrupos("grupos"));
        grupoDTO.add(this.linkToResource.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(this.linkToResource.linkToGrupos());
    }
}
