package br.com.gew.domain.services;

import br.com.gew.domain.entities.Secao;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.SecoesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SecoesService {

    private SecoesRepository secoesRepository;

    private FuncionariosSecoesService funcionariosSecoesService;

    public List<Secao> listar() throws ExceptionTratement {
        try {
            return secoesRepository.findAll();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Secao> buscar(long id) throws ExceptionTratement {
        try {
            return secoesRepository.findById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Secao> buscarPorFuncionario(long funcionarioCracha) throws ExceptionTratement {
        try {
            return secoesRepository.findById(
                    funcionariosSecoesService.buscarPorFuncionario(funcionarioCracha).getSecao_id()
            );
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Secao> buscarPorNome(String nome) throws ExceptionTratement {
        try {
            return secoesRepository.findByNome(nome);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
