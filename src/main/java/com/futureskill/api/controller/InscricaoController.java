package com.futureskill.api.controller;

import com.futureskill.api.dto.request.InscricaoRequest;
import com.futureskill.api.dto.response.InscricaoResponse;
import com.futureskill.api.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {
    
    @Autowired
    private InscricaoService inscricaoService;
    
    @PostMapping
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<InscricaoResponse> inscrever(@Valid @RequestBody InscricaoRequest request) {
        InscricaoResponse inscricao = inscricaoService.inscrever(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricao);
    }
    
    @GetMapping("/minhas")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<List<InscricaoResponse>> listarMinhasInscricoes() {
        List<InscricaoResponse> inscricoes = inscricaoService.listarMinhasInscricoes();
        return ResponseEntity.ok(inscricoes);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InscricaoResponse>> listarTodas() {
        List<InscricaoResponse> inscricoes = inscricaoService.listarTodas();
        return ResponseEntity.ok(inscricoes);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable Long id) {
        inscricaoService.cancelarInscricao(id);
        return ResponseEntity.noContent().build();
    }
}

