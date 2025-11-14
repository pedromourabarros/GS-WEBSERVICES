package com.futureskill.api.controller;

import com.futureskill.api.dto.request.CursoRequest;
import com.futureskill.api.dto.response.CursoResponse;
import com.futureskill.api.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;
    
    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarTodos() {
        List<CursoResponse> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        CursoResponse curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<CursoResponse>> buscarPorCategoria(@PathVariable String categoria) {
        List<CursoResponse> cursos = cursoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(cursos);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoResponse> criar(@Valid @RequestBody CursoRequest request) {
        CursoResponse curso = cursoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest request) {
        CursoResponse curso = cursoService.atualizar(id, request);
        return ResponseEntity.ok(curso);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

