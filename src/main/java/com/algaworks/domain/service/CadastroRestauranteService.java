package com.algaworks.domain.service;

import com.algaworks.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.model.FormaPagamento;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public Restaurante buscarOuFalhar(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long id){
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id){
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long idRestaurante, Long idFormaPagamento){
        Restaurante restaurante = this.buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);

        restaurante.desassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long idRestaurante, Long idFormaPagamento){
        Restaurante restaurante = this.buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);

        restaurante.associarFormaPagamento(formaPagamento);
    }

}
