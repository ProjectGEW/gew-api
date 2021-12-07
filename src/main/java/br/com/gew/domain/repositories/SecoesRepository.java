package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Secao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecoesRepository extends JpaRepository<Secao, Long> {

    Optional<Secao> findByNome(String nome);

}
