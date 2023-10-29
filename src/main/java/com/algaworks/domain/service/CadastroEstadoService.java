package com.algaworks.domain.service;

import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.domain.model.Estado;
import com.algaworks.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {
    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar (Estado estado){
        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir (Long id){
        try{
            estadoRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }

    public Estado buscarOuFalhar(Long id){
        return estadoRepository.findById(id).orElseThrow(() ->
                new EstadoNaoEncontradoException(id));
    }
}
