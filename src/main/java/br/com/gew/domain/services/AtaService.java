package br.com.gew.domain.services;

/*
* Classe responsável por realizar os métodos
* CRUD referente as Atas dos projetos
*
* ataRepositories: interface para linkar à tabela de atas
* projetoRepositories: interface para linkar à tabela de projetos
* */

import br.com.gew.domain.entities.Ata;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.AtasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Service
public class AtaService {

    private AtasRepository atasRepositories;
    private ProjetosService projetosService;

    @Transactional
    public Ata storeFile(MultipartFile file, long numeroDoProjeto) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if(fileName.contains("..")) {
                throw new ExceptionTratement("Sorry! Filename contains invalid path sequence " + fileName);
            }

            long projetoId = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

            if(atasRepositories.findByProjetoId(projetoId).isPresent()) {
                throw new ExceptionTratement("Já há uma ata atrelada a este projeto");
            }

            Ata ata = new Ata(
                    fileName,
                    file.getContentType(),
                    file.getBytes(),
                    LocalDateTime.now(),
                    projetosService.buscar(projetoId).get());

            return atasRepositories.save(ata);
        } catch (IOException ex) {
            throw new ExceptionTratement("Could not store file " + fileName + ". Please try again!");
        }
    }

    public Ata loadFileAsResource(long numeroDoProjeto) throws ExceptionTratement {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new ExceptionTratement("Projeto não encontrado");
        }

        long projetoId = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        return atasRepositories.findByProjetoId(projetoId)
                .orElseThrow(() -> new
                        EntityNotFoundException("Arquivo com este id não encontrado " + projetoId)
                );
    }

    public void removeFile(long numeroDoProjeto) {
        long projeto_id = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        String ata_id = atasRepositories.findByProjetoId(projeto_id).get().getId();

        atasRepositories.deleteById(ata_id);
    }

}
