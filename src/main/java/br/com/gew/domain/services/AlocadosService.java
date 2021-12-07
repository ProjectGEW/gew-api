package br.com.gew.domain.services;

import br.com.gew.domain.entities.Alocado;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.AlocadosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AlocadosService {

    private AlocadosRepository alocadosRepository;

    @Transactional
    public Alocado cadastrar(Alocado alocado) throws ExceptionTratement {
        try {
            return alocadosRepository.save(alocado);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Alocado> buscar(long funcionario_cracha, long projeto_id) throws ExceptionTratement {
        try {
            return alocadosRepository.findByFuncionarioCrachaAndProjetoId(
                    funcionario_cracha, projeto_id
            );
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Alocado> listar() throws ExceptionTratement {
        try {
            return alocadosRepository.findAll();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Alocado> listarPorFuncionario(long funcionario_cracha) throws ExceptionTratement {
        try {
            return alocadosRepository.findAllByFuncionarioCracha(
                    funcionario_cracha
            );
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Alocado> listarPorProjeto(long projeto_id) throws ExceptionTratement {
        try {
            return alocadosRepository.findAllByProjetoId(projeto_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    @Transactional
    public Alocado editar(Alocado alocado, long id) throws ExceptionTratement {
        try {
            alocado.setId(id);

            return alocadosRepository.save(alocado);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long id) throws ExceptionTratement {
        try {
            alocadosRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
