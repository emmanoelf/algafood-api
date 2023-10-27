package com.algaworks.algafoodapi;

import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CadastroCozinhaIntegrationsTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Chinesa");

        cozinha = cadastroCozinhaService.salvar(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        ConstraintViolationException error = Assertions
                .assertThrows(ConstraintViolationException.class, () ->{
            cadastroCozinhaService.salvar(cozinha);
        });
        assertThat(error).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
        EntidadeEmUsoException error = Assertions.assertThrows(
                EntidadeEmUsoException.class, () -> {
                    cadastroCozinhaService.excluir(1L);
                }
        );
        assertThat(error).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
        EntidadeNaoEncontradaException error = Assertions
                .assertThrows(EntidadeNaoEncontradaException.class, () -> {
                    cadastroCozinhaService.excluir(666L);
                });
        assertThat(error).isNotNull();
    }
}
