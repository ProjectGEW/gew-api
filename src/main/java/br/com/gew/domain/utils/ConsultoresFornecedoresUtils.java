package br.com.gew.domain.utils;

import br.com.gew.domain.entities.ConsultorFornecedor;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.ConsultoresFornecedoresService;
import br.com.gew.domain.services.FornecedoresService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConsultoresFornecedoresUtils {

    private FornecedoresService fornecedoresService;
    private ConsultoresFornecedoresService consultoresFornecedoresService;

    public void cadastrar(String fornecedorNome, long funcionarioCracha) throws ExceptionTratement {
        ConsultorFornecedor consultorFornecedor = new ConsultorFornecedor();

        consultorFornecedor.setFornecedor_id(
                fornecedoresService.buscarPorNome(fornecedorNome).getId()
        );
        consultorFornecedor.setFuncionario_cracha(funcionarioCracha);

        consultoresFornecedoresService.cadastrar(consultorFornecedor);
    }

    public void remover(long funcionarioCracha) throws ExceptionTratement {
        if (consultoresFornecedoresService.buscarPorFuncionario(funcionarioCracha) == null) {
            throw new ExceptionTratement("Consultor não possui fornecedor");
        }

        long id = consultoresFornecedoresService.buscarPorFuncionario(funcionarioCracha).getId();

        consultoresFornecedoresService.remover(id);
    }

}
