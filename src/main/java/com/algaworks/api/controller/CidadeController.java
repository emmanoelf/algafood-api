package com.algaworks.api.controller;

import com.algaworks.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.repository.CidadeRepository;
import com.algaworks.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar(){ return cidadeRepository.findAll(); }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id){
        return cadastroCidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try{
            return cadastroCidadeService.salvar(cidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
        try{
            Cidade getCidade = cadastroCidadeService.buscarOuFalhar(id);
            BeanUtils.copyProperties(cidade, getCidade, "id");
            return cadastroCidadeService.salvar(getCidade);
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
