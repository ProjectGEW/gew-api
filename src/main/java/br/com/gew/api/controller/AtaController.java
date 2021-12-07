package br.com.gew.api.controller;

/*
* Classe responsável por controlar as rotas
* CRUD relacionadas às atas
* */

import br.com.gew.api.model.output.AtaOutputDTO;
import br.com.gew.domain.entities.Ata;
import br.com.gew.domain.services.AtaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/files")
public class AtaController {

    private AtaService ataService;

    @PostMapping("/upload/{numeroDoProjeto}")
    public AtaOutputDTO uploadFile(
            @RequestParam("file")MultipartFile file,
            @PathVariable long numeroDoProjeto
    ) {
        Ata ata = ataService.storeFile(file, numeroDoProjeto);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(String.valueOf(numeroDoProjeto))
                .toUriString();

        return new AtaOutputDTO(ata.getId(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @DeleteMapping("/remove/{numeroDoProjeto}")
    public void removeFile(@PathVariable long numeroDoProjeto) {
        ataService.removeFile(numeroDoProjeto);
    }

    @GetMapping("/download/{numeroDoProjeto}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long numeroDoProjeto) {

        Ata ata = ataService.loadFileAsResource(numeroDoProjeto);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(ata.getTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ata.getNome() + "\"")
                .body(new ByteArrayResource(ata.getData()));
    }

}
