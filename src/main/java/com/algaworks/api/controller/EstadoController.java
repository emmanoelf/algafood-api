package com.algaworks.api.controller;


import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.domain.model.Estado;
import com.algaworks.domain.repository.EstadoRepository;
import com.algaworks.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){ return estadoRepository.findAll(); }

    @GetMapping("/{id}")
    public Estado buscar(@PathVariable Long id){
        return cadastroEstadoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    public Estado alterar(@PathVariable Long id, @RequestBody Estado estado){
        Estado getEstado = cadastroEstadoService.buscarOuFalhar(id);

        BeanUtils.copyProperties(estado, getEstado, "id");
        return cadastroEstadoService.salvar(getEstado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long id){
        cadastroEstadoService.excluir(id);
    }
}
