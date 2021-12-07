package br.com.gew.domain.services;

import br.com.gew.domain.entities.Skill;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.SkillsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SkillsService {

    private SkillsRepository skillsRepository;

    @Transactional
    public Skill cadastrar(Skill skill) throws ExceptionTratement {
        try {
            return skillsRepository.save(skill);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Skill> listar() throws ExceptionTratement {
        try {
            return skillsRepository.findAll();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Skill> buscar(long id) throws ExceptionTratement {
        try {
            return skillsRepository.findById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Skill> buscarPorNome(String nome) throws ExceptionTratement {
        try {
            return skillsRepository.findByNome(nome);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long id) throws ExceptionTratement {
        try {
            skillsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
