package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargosRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByNome(String nome);

}
