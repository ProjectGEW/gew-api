package br.com.gew.domain.services;

import br.com.gew.domain.entities.Projeto;
import br.com.gew.domain.entities.StatusProjeto;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.ProjetosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjetosService {

    private ProjetosRepository projetosRepository;

    @Transactional
    public Projeto cadastrar(Projeto projeto) throws ExceptionTratement {
        try {
            return projetosRepository.save(projeto);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Projeto> buscar(long id) throws ExceptionTratement {
        try {
            return projetosRepository.findById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Projeto> buscarPorNumeroProjeto(long numeroDoProjeto) throws ExceptionTratement {
        try {
            return projetosRepository.findByNumeroDoProjeto(numeroDoProjeto);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public Optional<Projeto> buscarPorTitulo(String titulo) throws ExceptionTratement {
        try {
            return projetosRepository.findByTitulo(titulo);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Projeto> listar() throws ExceptionTratement {
        try {
            return projetosRepository.findAll();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Projeto> listarPorStatus(StatusProjeto statusProjeto) throws ExceptionTratement {
        try {
            return projetosRepository.findAllByStatusProjeto(statusProjeto);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<Projeto> listarPorDataConclusao(LocalDate data) throws ExceptionTratement {
        try {
            return projetosRepository.findAllByDataDaConclusao(data);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    @Transactional
    public Projeto editar(Projeto projeto, long numeroDoProjeto) throws ExceptionTratement {
        projeto.setId(
                projetosRepository.findByNumeroDoProjeto(numeroDoProjeto).get().getId()
        );
        projeto.setNumeroDoProjeto(numeroDoProjeto);

        try {
            return projetosRepository.save(projeto);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long projetoId) throws ExceptionTratement {
        try {
            projetosRepository.deleteById(projetoId);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
