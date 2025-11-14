package com.futureskill.api.service;

import com.futureskill.api.dto.request.CursoRequest;
import com.futureskill.api.dto.response.CursoResponse;
import com.futureskill.api.exception.ResourceNotFoundException;
import com.futureskill.api.model.Curso;
import com.futureskill.api.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Transactional(readOnly = true)
    public List<CursoResponse> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CursoResponse buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + id));
        return toResponse(curso);
    }
    
    @Transactional
    public CursoResponse criar(CursoRequest request) {
        Curso curso = new Curso();
        curso.setTitulo(request.getTitulo());
        curso.setDescricao(request.getDescricao());
        curso.setCategoria(request.getCategoria());
        curso.setCargaHoraria(request.getCargaHoraria());
        
        curso = cursoRepository.save(curso);
        return toResponse(curso);
    }
    
    @Transactional
    public CursoResponse atualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + id));
        
        curso.setTitulo(request.getTitulo());
        curso.setDescricao(request.getDescricao());
        curso.setCategoria(request.getCategoria());
        curso.setCargaHoraria(request.getCargaHoraria());
        
        curso = cursoRepository.save(curso);
        return toResponse(curso);
    }
    
    @Transactional
    public void excluir(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso não encontrado com ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<CursoResponse> buscarPorCategoria(String categoria) {
        return cursoRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    private CursoResponse toResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getCategoria(),
                curso.getCargaHoraria()
        );
    }
}

