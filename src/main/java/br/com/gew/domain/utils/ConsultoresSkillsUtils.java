package br.com.gew.domain.utils;

import br.com.gew.domain.entities.ConsultorSkill;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.ConsultoresSkillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConsultoresSkillsUtils {

    private ConsultoresSkillsService consultoresSkillsService;

    public ConsultorSkill cadastrar(long funcionario_cracha, long skill_id) throws ExceptionTratement {
        ConsultorSkill consultorSkill = new ConsultorSkill();

        consultorSkill.setFuncionario_cracha(funcionario_cracha);
        consultorSkill.setSkill_id(skill_id);

        return consultoresSkillsService.cadastrar(consultorSkill);
    }

    public List<ConsultorSkill> listarPorSkill(long skill_id) throws ExceptionTratement {
        return consultoresSkillsService.listarPorSkill(skill_id);
    }

    public void remover(long funcionario_cracha) throws ExceptionTratement {
        if (consultoresSkillsService.listarPorConsultor(funcionario_cracha) == null) {
            throw new ExceptionTratement("Consultor não possui skills");
        }

        List<ConsultorSkill> consultorSkills = consultoresSkillsService
                .listarPorConsultor(funcionario_cracha);

        for (ConsultorSkill consultorSkill : consultorSkills) {
            consultoresSkillsService.remover(consultorSkill.getId());
        }
    }

}
