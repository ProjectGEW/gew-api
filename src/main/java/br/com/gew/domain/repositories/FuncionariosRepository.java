package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByTelefone(String telefone);

    Optional<Funcionario> findByEmail(String email);

}
