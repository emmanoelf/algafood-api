package com.algaworks.domain.service;

import com.algaworks.domain.exception.EntidadeEmUsoException;
import com.algaworks.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.domain.model.Grupo;
import com.algaworks.domain.repository.GrupoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {
    private static final String MSG_GRUPO_EM_USO
            = "Grupo de código %d não pode ser removido, pois está em uso";

    @Autowired
    private GrupoRespository grupoRespository;

    @Transactional
    public Grupo salvar(Grupo grupo) { return this.grupoRespository.save(grupo); }

    @Transactional
    public void excluir(Long id){
        try{
            this.grupoRespository.deleteById(id);
            this.grupoRespository.flush();
        }catch(EmptyResultDataAccessException e){
            throw new GrupoNaoEncontradoException(id);
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    public Grupo buscarOuFalhar(Long id){
        return this.grupoRespository.findById(id).orElseThrow(() ->
                new GrupoNaoEncontradoException(id));
    }
}
