package br.com.gew.domain.services;

import br.com.gew.domain.entities.Despesa;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.DespesasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DespesasService {

    private DespesasRepository despesasRepository;

    @Transactional
    public Despesa cadastrar(Despesa despesa) throws ExceptionTratement {
        try {
            return despesasRepository.save(despesa);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Despesa> buscar(long id) throws ExceptionTratement {
        try {
            return despesasRepository.findById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Despesa> listarPorProjeto(long projetoId) throws ExceptionTratement {
        try {
            return despesasRepository.findAllByProjetoId(projetoId);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    @Transactional
    public Despesa editar(Despesa despesa, long despesa_id) throws ExceptionTratement {
        try {
            despesa.setId(despesa_id);

            return despesasRepository.save(despesa);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long despesa_id) throws ExceptionTratement {
        try {
            despesasRepository.deleteById(despesa_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
