package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.AlocadoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlocadosLogsRepository extends JpaRepository<AlocadoLog, Long> {

    @Query("SELECT a FROM AlocadoLog a WHERE a.alocado_id = ?1")
    List<AlocadoLog> findAllByAlocadoId(long alocado_id);

}
