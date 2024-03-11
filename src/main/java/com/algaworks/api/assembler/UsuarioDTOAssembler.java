package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.UsuarioController;
import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public UsuarioDTOAssembler(){
        super(UsuarioController.class, UsuarioDTO.class);
    }

    public UsuarioDTO toModel(Usuario usuario){
        UsuarioDTO usuarioDto = createModelWithId(usuario.getId(), usuario);
        this.modelMapper.map(usuario, usuarioDto);

        usuarioDto.add(this.linkToResource.linkToUsuarios("usuarios"));

        usuarioDto.add(this.linkToResource.linkToUsuario(usuarioDto.getId(), "grupos-usuario"));

        return usuarioDto;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }
}
