package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.ConsultorFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultoresFornecedoresRepository extends JpaRepository<ConsultorFornecedor, Long> {

    @Query("SELECT c FROM ConsultorFornecedor c WHERE c.funcionario_cracha = ?1")
    Optional<ConsultorFornecedor> findByFuncionarioCracha(long funcionario_cracha);

}
