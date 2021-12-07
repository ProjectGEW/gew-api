package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesasRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByProjetoId(long projetoId);

}
