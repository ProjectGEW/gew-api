package br.com.gew.domain.repositories;

import br.com.gew.domain.entities.ConsultorSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultoresSkillsRepository extends JpaRepository<ConsultorSkill, Long> {

    @Query("SELECT c FROM ConsultorSkill c WHERE c.funcionario_cracha = ?1")
    List<ConsultorSkill> findAllByFuncionarioCracha(long funcionario_cracha);

    @Query("SELECT c FROM ConsultorSkill c WHERE c.skill_id = ?1")
    List<ConsultorSkill> findAllBySkillId(long skill_id);

}
