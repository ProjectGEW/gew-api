package br.com.gew.api.controller;

import br.com.gew.api.assembler.SecaoAssembler;
import br.com.gew.api.model.output.SecaoOutputDTO;
import br.com.gew.domain.services.SecoesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/secoes")
public class SecaoController {

    private SecaoAssembler secaoAssembler;
    private SecoesService secoesService;

    @GetMapping
    public ResponseEntity<List<SecaoOutputDTO>> listar() {
        if (secoesService.listar().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(secaoAssembler.toCollectionModel(secoesService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecaoOutputDTO> buscar(@PathVariable long id) {
        if (secoesService.buscar(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(secaoAssembler.toModel(secoesService.buscar(id).get()));
    }

    @GetMapping("/cracha/{funcionario_cracha}")
    public ResponseEntity<SecaoOutputDTO> buscarPorFuncionario(@PathVariable long funcionario_cracha) {
        if (secoesService.buscarPorFuncionario(funcionario_cracha).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                secaoAssembler.toModel(secoesService.buscarPorFuncionario(funcionario_cracha).get())
        );
    }

}
