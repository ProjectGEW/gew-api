package br.com.gew.domain.utils;

import br.com.gew.domain.entities.*;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AlocadosUtils {

    private ProjetosService projetosService;
    private AlocadosService alocadosService;
    private CargosService cargosService;
    private CargosFuncionariosService cargosFuncionariosService;
    private DespesasService despesasService;
    private AlocadosLogsService alocadosLogsService;
    private LogHorasService logHorasService;
    private FuncionariosService funcionariosService;

    public void alocarConsultor(
            long numeroDoProjeto,
            long funcionario_cracha,
            int horas
    ) throws ExceptionTratement {
        Alocado alocado = new Alocado();

        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        alocado.setHoras_totais(horas);
        alocado.setStatus(true);
        alocado.setFuncionario_cracha(funcionario_cracha);
        alocado.setProjeto_id(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        if (alocadosService.buscar(
                funcionario_cracha, projeto_id
        ).isPresent()) {
            Alocado alocadoBD = alocadosService.buscar(funcionario_cracha, projeto_id).get();

            if (!alocadoBD.isStatus()) {
                alocado.setHoras_totais(alocadoBD.getHoras_totais() + horas);

                alocadosService.editar(alocado, alocadoBD.getId());
            }
        }

        alocadosService.cadastrar(alocado);
    }

    public void desalocarConsultor(
            long numero_projeto,
            long funcionario_cracha
    ) throws ExceptionTratement {
        long projeto_id = projetosService.buscarPorNumeroProjeto(numero_projeto).get().getId();

        Alocado alocado = alocadosService.buscar(funcionario_cracha, projeto_id).get();

        List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

        if (alocadoLogs.isEmpty()) {
            alocadosService.remover(alocado.getId());
        } else {
            int totalApontamento = 0;
            alocado.setStatus(false);

            for (AlocadoLog alocadoLog : alocadoLogs) {
                totalApontamento += logHorasService.buscar(alocadoLog.getLog_id()).get().getHoras();
            }

            alocado.setHoras_totais(totalApontamento);
            alocadosService.editar(alocado, alocado.getId());
        }
    }

    public boolean verifyExceptionAlocacao(int horas, long funcionario_cracha, long numeroDoProjeto) {
        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        boolean alreadyExistsValidation = alocadosService.buscar(funcionario_cracha, projeto_id).isPresent() &&
                alocadosService.buscar(funcionario_cracha, projeto_id).get().isStatus();

        if (alreadyExistsValidation) {
            throw new ExceptionTratement("Consultor já está alocado neste projeto");
        }

        boolean consultorValidation = cargosService.buscar(
                cargosFuncionariosService.buscarPorFuncionario(funcionario_cracha).getCargo_id()
        ).getNome().equals("ROLE_CONSULTOR");


        if (!consultorValidation) {
            throw new ExceptionTratement("Não é consultor");
        }

        int horas_aprovadas = 0;

        List<Despesa> despesasDoProjeto = despesasService.listarPorProjeto(projeto_id);

        for (Despesa despesas : despesasDoProjeto) {
            horas_aprovadas += despesas.getEsforco();
        }

        Projeto projeto = projetosService.buscar(projeto_id).get();

        if (horas + projeto.getHoras_apontadas()  > horas_aprovadas) {
            throw new ExceptionTratement("Limite de horas excedidos, neste projeto ainda restam " +
                    (horas_aprovadas - projeto.getHoras_apontadas()) + " horas");
        }

        return false;
    }

    public boolean verifyExceptionApontamento(long funcionario_cracha, long numeroDoProjeto) {
        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        boolean alreadyExistsValidation = alocadosService.buscar(funcionario_cracha, projeto_id).isPresent();

        if (!alreadyExistsValidation) {
            throw new ExceptionTratement("Consultor não está alocado neste projeto");
        }

        return false;
    }

    public boolean verifyExceptionDesalocacao(long funcionario_cracha, long numeroDoProjeto) {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new ExceptionTratement("Projeto não encontrado");
        }

        if (alocadosService.listarPorFuncionario(funcionario_cracha).isEmpty()) {
            throw new ExceptionTratement("Consultor não está alocado");
        }

        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        if (!alocadosService.buscar(funcionario_cracha, projeto_id).get().isStatus()) {
            throw new ExceptionTratement("Consultor já está desalocado");
        }

        return false;
    }

}
