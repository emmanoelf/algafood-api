package com.algaworks.domain.service;

import com.algaworks.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {
    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId){
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(cozinhaId);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }
}
