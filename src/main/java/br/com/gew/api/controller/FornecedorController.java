package br.com.gew.api.controller;

import br.com.gew.api.assembler.FornecedorAssembler;
import br.com.gew.api.model.output.FornecedorOutputDTO;
import br.com.gew.domain.services.FornecedoresService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private FornecedoresService fornecedoresService;

    private FornecedorAssembler fornecedorAssembler;

    @GetMapping
    public ResponseEntity<List<FornecedorOutputDTO>> listar() {
        if (fornecedoresService.listar().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                fornecedorAssembler.toCollectionModel(fornecedoresService.listar())
        );
    }

}
