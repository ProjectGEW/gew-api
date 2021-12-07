package br.com.gew.domain.services;

import br.com.gew.domain.entities.Cargo;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.CargosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CargosService {

    private CargosRepository cargosRepository;

    public Cargo buscar(long id) throws EntityNotFoundException {
        try {
            return cargosRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Cargo buscarPorNome(String nome) throws EntityNotFoundException {
        try {
            return cargosRepository.findByNome(nome).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
