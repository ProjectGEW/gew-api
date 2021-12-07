package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.LogHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LogHorasRepository extends JpaRepository<LogHoras, Long> {

    @Query("SELECT l FROM LogHoras l WHERE l.data = ?1")
    List<LogHoras> findAllByData(LocalDate date);

}
