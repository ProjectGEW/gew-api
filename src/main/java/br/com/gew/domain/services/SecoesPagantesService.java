package br.com.gew.domain.services;

import br.com.gew.domain.entities.SecaoPagante;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.SecoesPagantesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SecoesPagantesService {

    private SecoesPagantesRepository secoesPagantesRepository;

    @Transactional
    public SecaoPagante cadastrar(SecaoPagante secaoPagante) throws ExceptionTratement {
        try {
            return secoesPagantesRepository.save(secaoPagante);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<SecaoPagante> listarPorProjeto(long projetoId) throws ExceptionTratement {
        try {
            return secoesPagantesRepository.findAllByProjetoId(projetoId);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    @Transactional
    public SecaoPagante editar(
            SecaoPagante secaoPagante,
            long secao_pagante_id
    ) throws ExceptionTratement {
        try {
            secaoPagante.setId(secao_pagante_id);

            return secoesPagantesRepository.save(secaoPagante);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long secao_pagante_id) throws ExceptionTratement {
        try {
            secoesPagantesRepository.deleteById(secao_pagante_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
