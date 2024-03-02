package com.algaworks.api.controller;

import com.algaworks.api.assembler.CidadeDTOAssembler;
import com.algaworks.api.assembler.CidadeInputDisassembler;
import com.algaworks.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.api.model.CidadeDTO;
import com.algaworks.api.model.input.CidadeInput;
import com.algaworks.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.repository.CidadeRepository;
import com.algaworks.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeDTO> listar(){
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeDTOAssembler.toCollectionDTO(cidades);
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable Long id){
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(id);
        return cidadeDTOAssembler.toDTO(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cadastroCidadeService.salvar(cidade);
            return cidadeDTOAssembler.toDTO(cidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade getCidade = cadastroCidadeService.buscarOuFalhar(id);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, getCidade);
            getCidade = cadastroCidadeService.salvar(getCidade);
            return cidadeDTOAssembler.toDTO(getCidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCidadeService.excluir(id);
    }

}
