package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Ata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtasRepository extends JpaRepository<Ata, String> {

    Optional<Ata> findByProjetoId(long projeto_id);

}
