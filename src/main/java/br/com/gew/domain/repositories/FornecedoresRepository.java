package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedoresRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByNome(String nome);

}
