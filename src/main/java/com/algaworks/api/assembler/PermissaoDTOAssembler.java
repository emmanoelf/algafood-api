package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.model.PermissaoDTO;
import com.algaworks.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDTOAssembler implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    @Override
    public PermissaoDTO toModel(Permissao permissao) {
        PermissaoDTO permissaoDto = this.modelMapper.map(permissao, PermissaoDTO.class);
        return permissaoDto;
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(this.linkToResource.linkToPermissoes());
    }
}
