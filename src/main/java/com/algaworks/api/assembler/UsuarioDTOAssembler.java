package com.algaworks.api.assembler;

import com.algaworks.api.controller.UsuarioController;
import com.algaworks.api.controller.UsuarioGrupoController;
import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTOAssembler(){
        super(UsuarioController.class, UsuarioDTO.class);
    }

    public UsuarioDTO toModel(Usuario usuario){
        UsuarioDTO usuarioDto = createModelWithId(usuario.getId(), usuario);
        this.modelMapper.map(usuario, usuarioDto);

        usuarioDto.add(WebMvcLinkBuilder
                .linkTo(UsuarioController.class).withSelfRel());

        usuarioDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(usuario.getId())).withRel("usuarios"));

        return usuarioDto;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }
}
