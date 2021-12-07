package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByNome(String nome);

}
