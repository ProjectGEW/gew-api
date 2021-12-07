package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Projeto;
import br.com.gew.domain.entities.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetosRepository extends JpaRepository<Projeto, Long> {

    Optional<Projeto> findByNumeroDoProjeto(long numeroDoProjeto);

    Optional<Projeto> findByTitulo(String titulo);

    List<Projeto> findAllByStatusProjeto(StatusProjeto statusProjeto);

    List<Projeto> findAllByDataDaConclusao(LocalDate data);

}
