package br.com.gew.domain.utils;

import br.com.gew.domain.entities.Skill;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.SkillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SkillsUtils {

    private SkillsService skillsService;
    private ConsultoresSkillsUtils consultoresSkillsUtils;

    public void cadastrar(List<String> skills, long funcionario_cracha) throws Exception {
        for (String skillNome : skills) {
            Skill skill = new Skill();
            if (skillsService.buscarPorNome(skillNome).isEmpty()) {
                skill.setNome(skillNome);

                skill = skillsService.cadastrar(skill);
            } else {
                skill = skillsService.buscarPorNome(skillNome).get();
            }
            consultoresSkillsUtils.cadastrar(funcionario_cracha, skill.getId());
        }
    }

    public void editar(List<String> skills, long funcionario_cracha) throws Exception {
        for (String skillNome : skills) {
            Skill skill = new Skill();
            if (skillsService.buscarPorNome(skillNome).isEmpty()) {
                skill.setNome(skillNome);

                skill = skillsService.cadastrar(skill);
            } else {
                skill = skillsService.buscarPorNome(skillNome).get();
            }
            consultoresSkillsUtils.cadastrar(funcionario_cracha, skill.getId());
        }
    }

    public void remover(long funcionario_cracha) throws ExceptionTratement {
        consultoresSkillsUtils.remover(funcionario_cracha);

        List<Skill> skills = skillsService.listar();

        for (Skill skill : skills) {
            if (consultoresSkillsUtils.listarPorSkill(skill.getId()).isEmpty()) {
                skillsService.remover(skill.getId());
            }
        }
    }

}
