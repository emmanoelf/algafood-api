package com.algaworks.api.assembler;

import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario usuario){
        return this.modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios){
        return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
