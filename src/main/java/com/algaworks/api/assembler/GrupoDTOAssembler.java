package com.algaworks.api.assembler;

import com.algaworks.api.model.GrupoDTO;
import com.algaworks.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toDTO(Grupo grupo){
        return this.modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionList(List<Grupo> grupos){
        return grupos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
