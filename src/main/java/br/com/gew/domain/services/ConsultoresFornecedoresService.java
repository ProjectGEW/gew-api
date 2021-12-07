package br.com.gew.domain.services;

import br.com.gew.domain.entities.ConsultorFornecedor;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.ConsultoresFornecedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ConsultoresFornecedoresService {

    private ConsultoresFornecedoresRepository consultoresFornecedoresRepository;

    @Transactional
    public ConsultorFornecedor cadastrar(ConsultorFornecedor consultorFornecedor) throws ExceptionTratement {
        try {
            return consultoresFornecedoresRepository.save(consultorFornecedor);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public ConsultorFornecedor buscarPorFuncionario(long funcionario_cracha) throws ExceptionTratement {
        try {
            return consultoresFornecedoresRepository.findByFuncionarioCracha(funcionario_cracha).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long id) throws ExceptionTratement {
        try {
            consultoresFornecedoresRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
