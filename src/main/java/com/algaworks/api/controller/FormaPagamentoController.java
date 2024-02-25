package com.algaworks.api.controller;

import com.algaworks.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.api.model.input.FormaPagamentoInput;
import com.algaworks.domain.model.FormaPagamento;
import com.algaworks.domain.repository.FormaPagamentoRepository;
import com.algaworks.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = this.formaPagamentoRepository.getDataUltimaAtualizacao();

        if(dataUltimaAtualizacao != null){
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        List<FormaPagamento> formasPagamentos = this.formaPagamentoRepository.findAll();
        List<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler.toColletionDTO(formasPagamentos);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id, ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime ultimaAtualizacao = this.formaPagamentoRepository.getDataUltimaAtualizacaoById(id);

        if(ultimaAtualizacao != null){
            eTag = String.valueOf(ultimaAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;
        }

        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(id);
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoDTOAssembler.toDTO(formaPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoDTO);
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
