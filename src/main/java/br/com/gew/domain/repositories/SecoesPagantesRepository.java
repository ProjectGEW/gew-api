package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.SecaoPagante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecoesPagantesRepository extends JpaRepository<SecaoPagante, Long> {

    List<SecaoPagante> findAllByProjetoId(long projetoId);

}
