package com.algaworks.api.assembler;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.controller.FormaPagamentoController;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkToResource linkToResource;

    public FormaPagamentoDTOAssembler(){
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento){
        FormaPagamentoDTO formaPagamentoDto = createModelWithId(formaPagamento.getId(), formaPagamento);

        this.modelMapper.map(formaPagamento, formaPagamentoDto);

        formaPagamentoDto.add(this.linkToResource.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoDto;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(this.linkToResource.linkToFormasPagamento());
    }
}
