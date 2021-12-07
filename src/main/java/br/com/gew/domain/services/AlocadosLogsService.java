package br.com.gew.domain.services;

import br.com.gew.domain.entities.AlocadoLog;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.AlocadosLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AlocadosLogsService {

    private AlocadosLogsRepository alocadosLogsRepository;

    @Transactional
    public AlocadoLog cadastrar(AlocadoLog alocadoLog) throws ExceptionTratement {
        try {
            return alocadosLogsRepository.save(alocadoLog);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<AlocadoLog> listarPorAlocado(long alocado_id) throws ExceptionTratement {
        try {
            return alocadosLogsRepository.findAllByAlocadoId(alocado_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover() {

    }

}
