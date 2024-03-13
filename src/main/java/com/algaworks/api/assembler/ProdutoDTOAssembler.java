package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.RestauranteProdutoController;
import com.algaworks.api.model.ProdutoDTO;
import com.algaworks.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public ProdutoDTOAssembler(){
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

        this.modelMapper.map(produto, produtoDTO);

        produtoDTO.add(this.linkToResource.linkToProdutos(produto.getId(), "produtos"));

        return produtoDTO;
    }
}
