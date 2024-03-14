package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.RestauranteProdutoFotoController;
import com.algaworks.api.model.FotoProdutoDTO;
import com.algaworks.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public FotoProdutoDTOAssembler(){
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    @Override
    public FotoProdutoDTO toModel(FotoProduto fotoProduto){
        FotoProdutoDTO fotoProdutoDTO = this.modelMapper.map(fotoProduto, FotoProdutoDTO.class);

        fotoProdutoDTO.add(this.linkToResource
                .linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

        fotoProdutoDTO.add(this.linkToResource
                .linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));

        return fotoProdutoDTO;
    }
}
