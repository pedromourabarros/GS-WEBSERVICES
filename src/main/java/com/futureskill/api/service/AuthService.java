package com.futureskill.api.service;

import com.futureskill.api.config.JwtService;
import com.futureskill.api.dto.request.LoginRequest;
import com.futureskill.api.dto.request.RegisterRequest;
import com.futureskill.api.dto.response.AuthResponse;
import com.futureskill.api.dto.response.UsuarioResponse;
import com.futureskill.api.exception.BusinessException;
import com.futureskill.api.model.Usuario;
import com.futureskill.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Transactional
    public UsuarioResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já está em uso");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(request.getRole());
        
        usuario = usuarioRepository.save(usuario);
        
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
        
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", usuario.getRole().name());
        extraClaims.put("id", usuario.getId());
        
        String token = jwtService.generateToken(userDetails, extraClaims);
        
        return new AuthResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }
}

