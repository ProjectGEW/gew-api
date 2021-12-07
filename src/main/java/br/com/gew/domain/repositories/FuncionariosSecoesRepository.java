package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.FuncionarioSecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionariosSecoesRepository extends JpaRepository<FuncionarioSecao, Long> {

    @Query("SELECT f FROM FuncionarioSecao f WHERE f.funcionario_cracha = ?1")
    Optional<FuncionarioSecao> findByFuncionarioCracha(long funcionarioCracha);

}
