package com.algaworks.core.modelmapper;

import com.algaworks.api.model.EnderecoDTO;
import com.algaworks.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Endereco, EnderecoDTO> typeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        typeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDtoDestination, value) -> enderecoDtoDestination.getCidade().setEstado(value));

        return modelMapper;
    }
}
