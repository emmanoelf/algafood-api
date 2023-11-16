package com.algaworks.api.controller;

import com.algaworks.api.assembler.CozinhaDTOAssembler;
import com.algaworks.api.assembler.CozinhaInputDisassembler;
import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.api.model.input.CozinhaInput;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.repository.CozinhaRepository;
import com.algaworks.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(value="/cozinhas" /*produces = MediaType.APPLICATION_JSON_VALUE*/)
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public Page<CozinhaDTO> listar(Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDTO> cozinhasDTO = cozinhaDTOAssembler.toColletionDTO(cozinhasPage.getContent());
        Page<CozinhaDTO> cozinhasPageDTO = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
        return cozinhasPageDTO;
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId){
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        return cozinhaDTOAssembler.toDTO(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinhaService.salvar(cozinha);
        return cozinhaDTOAssembler.toDTO(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
        return cozinhaDTOAssembler.toDTO(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId){
        cadastroCozinhaService.excluir(cozinhaId);
    }

}
