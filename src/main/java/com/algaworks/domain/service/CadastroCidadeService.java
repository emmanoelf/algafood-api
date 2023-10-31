package com.algaworks.domain.service;

import com.algaworks.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.model.Estado;
import com.algaworks.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    public static final String MSG_CODIGO_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id){
        try{
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_CODIGO_CIDADE_EM_USO,
                    id));
        }catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(id);
        }
    }

    public Cidade buscarOuFalhar(Long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }
}
