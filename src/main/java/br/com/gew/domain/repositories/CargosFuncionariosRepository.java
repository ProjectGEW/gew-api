package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.CargoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargosFuncionariosRepository extends JpaRepository<CargoFuncionario, Long> {

    @Query("SELECT c FROM CargoFuncionario c WHERE c.funcionario_cracha = ?1")
    Optional<CargoFuncionario> findByFuncionarioCracha(long funcionario_cracha);

    @Query("SELECT c FROM CargoFuncionario c WHERE c.cargo_id = ?1")
    List<CargoFuncionario> findAllByCargoId(long cargo_id);

}
