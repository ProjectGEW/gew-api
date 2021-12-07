package br.com.gew.domain.services;

import br.com.gew.domain.entities.Fornecedor;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.FornecedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FornecedoresService {

    private FornecedoresRepository fornecedoresRepository;

    @Transactional
    public Fornecedor cadastrar(Fornecedor fornecedor) throws ExceptionTratement {
        try {
            return fornecedoresRepository.save(fornecedor);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Fornecedor buscar(long id) throws EntityNotFoundException {
        try {
            return fornecedoresRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Fornecedor buscarPorNome(String nome) throws EntityNotFoundException {
        try {
            return fornecedoresRepository.findByNome(nome).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Fornecedor> listar() {
        try {
            return fornecedoresRepository.findAll();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
