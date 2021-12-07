package br.com.gew.domain.utils;

import br.com.gew.api.model.input.HorasInputDTO;
import br.com.gew.domain.entities.Alocado;
import br.com.gew.domain.entities.AlocadoLog;
import br.com.gew.domain.entities.LogHoras;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.AlocadosLogsService;
import br.com.gew.domain.services.AlocadosService;
import br.com.gew.domain.services.LogHorasService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class LogHorasUtils {

    private AlocadosService alocadosService;
    private AlocadosLogsService alocadosLogsService;
    private LogHorasService logHorasService;

    public Alocado apontar(
            HorasInputDTO horas,
            long projeto_id,
            long numero_cracha
    ) throws ExceptionTratement {
        double horas_apontadas = 0;
        Alocado alocado = alocadosService.buscar(numero_cracha, projeto_id).get();

        List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

        for (AlocadoLog alocadoLog : alocadoLogs) {
            horas_apontadas += logHorasService.buscar(alocadoLog.getLog_id()).get().getHoras();
        }

        if (horas.getHoras() > alocado.getHoras_totais() - horas_apontadas) {
            throw new ExceptionTratement("Horas apontadas maiores do que as permitidas");
        }

        if (horas.getHoras() + horas_apontadas == alocado.getHoras_totais()) {
            alocado.setStatus(false);

            alocadosService.editar(alocado, alocado.getId());
        }

        criarLog(horas, alocado.getId());

        return alocado;
    }

    private void criarLog(HorasInputDTO horas, long alocado_id) {
        LogHoras logHoras = new LogHoras();

        logHoras.setData(LocalDate.now());

        if (horas.getData() != null) {
            String[] data = horas.getData().split("/");
            String dataFormat = data[2] + "-" + data[1] + "-" + data[0];

            logHoras.setData(LocalDate.parse(dataFormat));
        }

        logHoras.setDescricao(horas.getDescricao());
        logHoras.setHoras(horas.getHoras());
        logHoras.setCriado_em(LocalDateTime.now());

        logHoras = logHorasService.cadastrar(logHoras);

        AlocadoLog alocadoLog = new AlocadoLog();

        alocadoLog.setAlocado_id(alocado_id);
        alocadoLog.setLog_id(logHoras.getId());

        alocadosLogsService.cadastrar(alocadoLog);
    }

}
