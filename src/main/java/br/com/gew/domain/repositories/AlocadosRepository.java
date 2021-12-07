package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Alocado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlocadosRepository extends JpaRepository<Alocado, Long> {

    @Query("SELECT a FROM Alocado a WHERE a.funcionario_cracha = ?1 AND a.projeto_id = ?2")
    Optional<Alocado> findByFuncionarioCrachaAndProjetoId(long funcionario_cracha, long projeto_id);

    @Query("SELECT a FROM Alocado a WHERE a.funcionario_cracha = ?1")
    List<Alocado> findAllByFuncionarioCracha(long funcionario_cracha);

    @Query("SELECT a FROM Alocado a WHERE a.projeto_id = ?1")
    List<Alocado> findAllByProjetoId(long projeto_id);

}
