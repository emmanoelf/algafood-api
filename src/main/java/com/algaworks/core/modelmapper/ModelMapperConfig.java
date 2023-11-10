package com.algaworks.core.modelmapper;

import com.algaworks.api.model.EnderecoDTO;
import com.algaworks.api.model.input.ItemPedidoInput;
import com.algaworks.domain.model.Endereco;
import com.algaworks.domain.model.ItemPedido;
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

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
