package br.com.gew.domain.services;

import br.com.gew.domain.entities.ConsultorSkill;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.ConsultoresSkillsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ConsultoresSkillsService {

    private ConsultoresSkillsRepository consultoresSkillsRepository;

    @Transactional
    public ConsultorSkill cadastrar(ConsultorSkill consultorSkill) throws ExceptionTratement {
        try {
            return consultoresSkillsRepository.save(consultorSkill);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<ConsultorSkill> listarPorConsultor(long funcionario_cracha) throws ExceptionTratement {
        try {
            return consultoresSkillsRepository.findAllByFuncionarioCracha(funcionario_cracha);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<ConsultorSkill> listarPorSkill(long skill_id) throws ExceptionTratement {
        try {
            return consultoresSkillsRepository.findAllBySkillId(skill_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long id) throws ExceptionTratement {
        try {
            consultoresSkillsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
