// CorridaController.java
package br.com.fiap.jadv.rm552525.checkpoint.controller;

import br.com.fiap.jadv.rm552525.checkpoint.dto.CorridaRequestDTO;
import br.com.fiap.jadv.rm552525.checkpoint.dto.CorridaResponseDTO;
import br.com.fiap.jadv.rm552525.checkpoint.service.CorridaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rm552525/corridas")
public class CorridaController {

    private final CorridaService corridaService;

    public CorridaController(CorridaService corridaService) {
        this.corridaService = corridaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorridaResponseDTO> getCorridaById(@PathVariable Long id) {
        CorridaResponseDTO corridaResponseDTO = corridaService.getCorridaById(id);
        return ResponseEntity.ok(corridaResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CorridaResponseDTO> criarCorrida(@RequestBody @Valid CorridaRequestDTO corridaRequestDTO) {
        CorridaResponseDTO corridaResponseDTO = corridaService.criarCorrida(corridaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(corridaResponseDTO);
    }

    @PutMapping("/{id}/situacao")
    public ResponseEntity<Void> atualizarSituacaoCorrida(@PathVariable Long id, @RequestBody char novaSituacao) {
        corridaService.atualizarSituacaoCorrida(id, novaSituacao);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/posicao-atual")
    public ResponseEntity<Void> atualizarPosicaoAtual(@PathVariable Long id, @RequestBody @Valid CorridaRequestDTO corridaRequestDTO) {
        corridaService.atualizarPosicaoAtual(id, corridaRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
