package com.algaworks.api.assembler;

import com.algaworks.api.model.PermissaoDTO;
import com.algaworks.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao){
        return this.modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes){
        return permissoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
