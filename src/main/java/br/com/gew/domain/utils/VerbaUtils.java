package br.com.gew.domain.utils;

import br.com.gew.api.model.output.HorasApontadasOutputDTO;
import br.com.gew.api.model.output.VerbaUtilizadaOutputDTO;
import br.com.gew.api.model.output.VerbaUtilizadaPorDiaOutputDTO;
import br.com.gew.domain.entities.Alocado;
import br.com.gew.domain.entities.AlocadoLog;
import br.com.gew.domain.entities.Funcionario;
import br.com.gew.domain.entities.LogHoras;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class VerbaUtils {

    private AlocadosService alocadosService;
    private ProjetosService projetosService;
    private FuncionariosService funcionariosService;
    private AlocadosLogsService alocadosLogsService;
    private LogHorasService logHorasService;

    public VerbaUtilizadaOutputDTO contarVerbaUtilizada(long numeroDoProjeto) throws ExceptionTratement {
        List<Alocado> alocados = verificaNumeroDoProjeto(numeroDoProjeto);

        VerbaUtilizadaOutputDTO verbaUtilizada = new VerbaUtilizadaOutputDTO();

        verbaUtilizada.setTotal(calculaTotalVerba(alocados));

        return verbaUtilizada;
    }

    private List<Alocado> verificaNumeroDoProjeto(long numeroDoProjeto) {
        List<Alocado> alocados;

        if (numeroDoProjeto == 0) {
            alocados = alocadosService.listar();
        } else {
            if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
                throw new ExceptionTratement("Projeto com este número não encontrado");
            }

            long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

            alocados = alocadosService.listarPorProjeto(projeto_id);
        }

        return alocados;
    }

    private double calculaTotalVerba(List<Alocado> alocados) throws ExceptionTratement {
        double total = 0;

        for (Alocado alocado : alocados) {
            Funcionario funcionario = funcionariosService.buscar(alocado.getFuncionario_cracha()).get();

            List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

            for (AlocadoLog alocadoLog : alocadoLogs) {
                total += logHorasService.buscar(alocadoLog.getLog_id()).get().getHoras() *
                        funcionario.getValor_hora();
            }
        }

        return total;
    }

    public List<VerbaUtilizadaPorDiaOutputDTO> calcularVerbaUtilizadaPorDia(
            int days, long numeroDoProjeto
    ) throws ExceptionTratement {
        LocalDate date = LocalDate.now();

        List<VerbaUtilizadaPorDiaOutputDTO> countBudgetResponses = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            VerbaUtilizadaPorDiaOutputDTO countBudgetResponse = new VerbaUtilizadaPorDiaOutputDTO();

            double verba = countVerbaUtilizadaPorDiaPorProjeto(date, numeroDoProjeto);

            countBudgetResponse.setVerbaUtilizada(verba);
            countBudgetResponse.setData(date.getDayOfMonth() + "/" + date.getMonth().getValue());

            date = date.minusDays(1);

            countBudgetResponses.add(countBudgetResponse);
        }

        return countBudgetResponses;
    }

    private double countVerbaUtilizadaPorDiaPorProjeto(LocalDate date, long numeroDoProjeto) {
        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        List<Alocado> alocados = alocadosService.listarPorProjeto(projeto_id);

        return calculaTotalVerbaPorDia(alocados, date);
    }

    private double calculaTotalVerbaPorDia(List<Alocado> alocados, LocalDate date) {
        double total = 0;

        for (Alocado alocado : alocados) {
            Funcionario funcionario = funcionariosService.buscar(alocado.getFuncionario_cracha()).get();

            List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

            for (AlocadoLog alocadoLog : alocadoLogs) {
                LogHoras logHoras = logHorasService.buscar(alocadoLog.getLog_id()).get();

                if (logHoras.getData().equals(date)) {
                    total += logHoras.getHoras() * funcionario.getValor_hora();
                }
            }
        }

        return total;
    }

    public List<HorasApontadasOutputDTO> horasApontadas(long numeroDoProjeto) {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new ExceptionTratement("Projeto não encontrado");
        }

        List<HorasApontadasOutputDTO> horasApontadasOutputDTOS = new ArrayList<>();
        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        List<Alocado> alocados = alocadosService.listarPorProjeto(projeto_id);

        for (Alocado alocado : alocados) {
            List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

            Funcionario funcionario = funcionariosService.buscar(alocado.getFuncionario_cracha()).get();

            for (AlocadoLog alocadoLog : alocadoLogs) {
                HorasApontadasOutputDTO horasApontadas = new HorasApontadasOutputDTO();

                LogHoras logHoras = logHorasService.buscar(alocadoLog.getLog_id()).get();

                horasApontadas.setHoras_apontadas(logHoras.getHoras());
                horasApontadas.setData(logHoras.getData().toString());
                horasApontadas.setNome_funcionario(funcionario.getNome());
                horasApontadas.setValor_hora(funcionario.getValor_hora());

                horasApontadasOutputDTOS.add(horasApontadas);
            }
        }

        return horasApontadasOutputDTOS;
    }

}
