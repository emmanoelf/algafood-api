package com.algaworks.api.controller;

import com.algaworks.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.api.model.input.FormaPagamentoInput;
import com.algaworks.domain.model.FormaPagamento;
import com.algaworks.domain.repository.FormaPagamentoRepository;
import com.algaworks.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar(){
        List<FormaPagamento> formasPagamentos = this.formaPagamentoRepository.findAll();
        return formaPagamentoDTOAssembler.toColletionDTO(formasPagamentos);
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(id);
        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);
        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO alterar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(id);
        this.formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
        formaPagamento = this.cadastroFormaPagamentoService.salvar(formaPagamento);
        return this.formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        this.cadastroFormaPagamentoService.excluir(id);
    }
}
