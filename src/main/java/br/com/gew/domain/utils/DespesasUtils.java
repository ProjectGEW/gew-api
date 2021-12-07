package br.com.gew.domain.utils;

import br.com.gew.api.assembler.DespesaAssembler;
import br.com.gew.api.model.input.DespesaInputDTO;
import br.com.gew.api.model.output.DespesaOutputDTO;
import br.com.gew.domain.entities.Despesa;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.DespesasService;
import br.com.gew.domain.services.ProjetosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DespesasUtils {

    private DespesaAssembler despesaAssembler;

    private DespesasService despesasService;

    private ProjetosService projetosService;

    public double cadastrar(
            List<DespesaInputDTO> despesaInputDTOS,
            long numeroDoProjeto
    ) throws ExceptionTratement {
        List<Despesa> despesas = despesaAssembler.toCollectionEntity(despesaInputDTOS);

        for (Despesa despesa : despesas) {
            despesa.setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());

            despesasService.cadastrar(despesa);
        }

        return calculaTotalDespesas(despesas);
    }

    private double calculaTotalDespesas(List<Despesa> despesas) {
        double total = 0;

        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }

        return total;
    }

    public List<DespesaOutputDTO> listar(long projetoId) throws ExceptionTratement {
        return despesaAssembler.toCollectionModel(
                despesasService.listarPorProjeto(projetoId)
        );
    }

    public double editar(
            List<DespesaInputDTO> despesaInputDTOS,
            long numeroDoProjeto
    ) throws ExceptionTratement {
        List<Despesa> despesas = despesaAssembler.toCollectionEntity(despesaInputDTOS);
        List<Despesa> despesasDB = despesasService.listarPorProjeto(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        for (int i = 0; i < despesasDB.size(); i ++) {
            despesas.get(i).setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());

            despesasService.editar(despesas.get(i), despesasDB.get(i).getId());
        }

        if (despesas.size() > despesasDB.size()) {
            for (int i = despesasDB.size(); i < despesas.size(); i ++) {
                despesas.get(i).setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());

                despesasService.cadastrar(despesas.get(i));
            }
        }

        return calculaTotalDespesas(despesas);
    }

    public void remover(long numeroDoProjeto) throws ExceptionTratement {
        List<Despesa> despesasDB = despesasService.listarPorProjeto(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        for (Despesa despesa : despesasDB) {
            despesasService.remover(despesa.getId());
        }
    }

}
