package com.algaworks.api.assembler;

import com.algaworks.api.model.input.UsuarioInput;
import com.algaworks.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput){
        return this.modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario){
        this.modelMapper.map(usuarioInput, usuario);
    }
}
