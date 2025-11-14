package com.futureskill.api.service;

import com.futureskill.api.dto.request.InscricaoRequest;
import com.futureskill.api.dto.response.InscricaoResponse;
import com.futureskill.api.exception.BusinessException;
import com.futureskill.api.exception.ResourceNotFoundException;
import com.futureskill.api.model.Inscricao;
import com.futureskill.api.model.Usuario;
import com.futureskill.api.model.Curso;
import com.futureskill.api.repository.InscricaoRepository;
import com.futureskill.api.repository.UsuarioRepository;
import com.futureskill.api.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoService {
    
    @Autowired
    private InscricaoRepository inscricaoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Transactional
    public InscricaoResponse inscrever(InscricaoRequest request) {
        String email = getCurrentUserEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + request.getCursoId()));
        
        if (inscricaoRepository.existsByUsuarioAndCurso(usuario, curso)) {
            throw new BusinessException("Você já está inscrito neste curso");
        }
        
        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setCurso(curso);
        
        inscricao = inscricaoRepository.save(inscricao);
        return toResponse(inscricao);
    }
    
    @Transactional(readOnly = true)
    public List<InscricaoResponse> listarMinhasInscricoes() {
        String email = getCurrentUserEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        return inscricaoRepository.findByUsuario(usuario).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<InscricaoResponse> listarTodas() {
        return inscricaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void cancelarInscricao(Long id) {
        String email = getCurrentUserEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada com ID: " + id));
        
        if (!inscricao.getUsuario().getId().equals(usuario.getId())) {
            throw new BusinessException("Você não tem permissão para cancelar esta inscrição");
        }
        
        inscricaoRepository.delete(inscricao);
    }
    
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("Usuário não autenticado");
        }
        return authentication.getName();
    }
    
    private InscricaoResponse toResponse(Inscricao inscricao) {
        return new InscricaoResponse(
                inscricao.getId(),
                inscricao.getUsuario().getId(),
                inscricao.getUsuario().getNome(),
                inscricao.getCurso().getId(),
                inscricao.getCurso().getTitulo(),
                inscricao.getDataInscricao()
        );
    }
}

